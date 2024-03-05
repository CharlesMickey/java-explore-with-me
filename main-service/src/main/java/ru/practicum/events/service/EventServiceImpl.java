package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ViewStatsDto;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.service.CategoriesService;
import ru.practicum.events.dto.*;
import ru.practicum.events.dto.EventRequestStatusUpdateResult;

import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.repository.EventRepo;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.locations.dto.LocationDto;

import ru.practicum.locations.model.Location;

import ru.practicum.locations.service.LocationService;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.requests.mapper.RequestMapper;
import ru.practicum.requests.model.Request;
import ru.practicum.requests.repository.RequestRepo;
import ru.practicum.stats.StatsService;
import ru.practicum.statuses.EventState;
import ru.practicum.statuses.RequestStatus;
import ru.practicum.statuses.SortStatus;
import ru.practicum.statuses.StateAction;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;
import ru.practicum.utils.Constants;
import ru.practicum.utils.customPageRequest.CustomPageRequest;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.*;


@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;
    private final UserService userService;
    private final StatsService statsService;
    private final EventMapper eventMapper;
    private final LocationService locationService;
    private final RequestRepo requestRepo;
    private final RequestMapper requestMapper;
    private final CategoriesService categoriesService;

    @Override
    public List<EventShortDto> getPublicEvents(String text,
                                               List<Long> categories,
                                               Boolean paid,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               Boolean onlyAvailable,
                                               String sort,
                                               Integer from,
                                               Integer size,
                                               HttpServletRequest request) {


        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Начало события не может быть позже окончания.");
        }

        SortStatus sortStatus = null;

        if (sort != null) {
            sortStatus = SortStatus.valueOf(sort.toUpperCase());
        }

        Pageable pageable = sort != null && sortStatus == SortStatus.EVENT_DATE
                ? CustomPageRequest.customOf(from, size, Sort.by("eventDate").descending())
                : CustomPageRequest.customOf(from, size);
        List<Event> events = eventRepo
                .findByParameters(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable).getContent();

        if (events.isEmpty()) return Collections.emptyList();

        Map<Long, Long> views = statsService.getViews(events);
        List<EventShortDto> shortDtos = eventMapper.eventToEventShortDto(events, views);

        statsService.createEndpointHit(request);

        if (sort != null && sortStatus == SortStatus.VIEWS) {

            shortDtos.sort(Comparator.comparing(EventShortDto::getViews).reversed());
        }

        return shortDtos;
    }

    @Override
    public EventFullDto getPublicEventById(Long id, HttpServletRequest request) {

        Event event = eventRepo.findByIdAndState(id, EventState.PUBLISHED).orElseThrow(() -> new NotFoundException(
                String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Event", id)));

        statsService.createEndpointHit(request);

        List<ViewStatsDto> views = statsService.getViewStats(
                event.getPublishedOn(),
                event.getEventDate(),
                Collections.singletonList(request.getRequestURI()),
                true);

        long hits = views.stream().findFirst().map(view -> Long.valueOf(view.getHits())).orElse(0L) + 1;

        return eventMapper.eventToEventFullDto(event, hits);
    }

    @Override
    public List<EventShortDto> getUserEvents(Long userId, int from, int size) {
        userService.checkExistUserById(userId);
        Pageable pageable = CustomPageRequest.customOf(from, size);
        List<Event> events = eventRepo.findAllByInitiatorId(userId, pageable).getContent();

        return eventMapper.eventToEventShortDto(events);
    }

    @Override
    @Transactional
    public EventFullDto createUserEvent(Long userId, NewEventDto newEventDto) {
        User user = userService.findUserById(userId);
        Category category = categoriesService.getCategoryById((newEventDto.getCategory()));

        Location location = locationService.getLocation(newEventDto.getLocation());

        checkTime(newEventDto.getEventDate(), 2);
        Event event = eventRepo.save(eventMapper.newEventDtoToEvent(newEventDto, user, location, category,
                LocalDateTime.now(), EventState.PENDING));

        return eventMapper.eventToEventFullDto(event, 0L);
    }

    @Override
    public EventFullDto getUserEventFull(Long userId, Long eventId) {
        Event event = checkInitiator(userId, eventId);

        long hits = getHits(eventId);

        return eventMapper.eventToEventFullDto(event, hits);
    }

    @Override
    @Transactional
    public EventFullDto updateUserEvent(Long userId, Long eventId, UpdateEventUserRequest ueur) {
        Event event = checkInitiator(userId, eventId);

        checkTime(ueur.getEventDate(), 2);
        if (event.getState().equals(EventState.PUBLISHED)) {
            throw new ConflictException(Constants.ERR_CANCELED_EVENTS);
        }

        updateLocation(ueur.getLocation(), event);
        updateCategory(ueur.getCategory(), event);

        updateBasicData(
                event,
                ueur.getAnnotation(),
                ueur.getDescription(),
                ueur.getPaid(),
                ueur.getTitle(),
                ueur.getRequestModeration(),
                ueur.getParticipantLimit(),
                ueur.getEventDate(),
                ueur.getStateAction(), false);

        Event updatedEvent = eventRepo.save(event);

        long hits = getHits(eventId);

        return eventMapper.eventToEventFullDto(updatedEvent, hits);
    }

    @Override
    public List<ParticipationRequestDto> getUserEventRequests(Long userId, Long eventId) {
        checkInitiator(userId, eventId);
        List<Request> requests = requestRepo.findAllByEventId(eventId);

        return requestMapper.requestToRequestDto(requests);
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateUserEventRequest(
            Long userId,
            Long eventId,
            EventRequestStatusUpdateRequest ersur) {


        Event event = checkInitiator(userId, eventId);

        List<Request> confirmed = new ArrayList<>();
        List<Request> rejected = new ArrayList<>();

        List<Request> requests = requestRepo.findAllByIdIn(ersur.getRequestIds());

        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {

            requests.forEach(request -> request.setStatus(RequestStatus.CONFIRMED));
            return new EventRequestStatusUpdateResult(
                    requestMapper.requestToRequestDto(requests), Collections.emptyList());
        }


        boolean isAllPending = requests.stream()
                .map(Request::getStatus)
                .allMatch(status -> status.equals(RequestStatus.PENDING));

        if (!isAllPending) {
            throw new ConflictException(Constants.ERR_CHANGE_STATUS);
        }

        if (ersur.getStatus().equals(RequestStatus.CONFIRMED)) {

            long participantLimit = event.getParticipantLimit();
            long participantCount = event.getConfirmedRequests() + ersur.getRequestIds().size();

            if (participantLimit != 0 && participantCount > participantLimit) {
                throw new ConflictException(Constants.ERR_LIMIT_REACHED);
            }

            for (Request request : requests) {
                if (confirmed.size() < participantLimit) {
                    request.setStatus(RequestStatus.CONFIRMED);
                    confirmed.add(request);
                } else {
                    request.setStatus(RequestStatus.REJECTED);
                    rejected.add(request);
                }
            }

        } else if (ersur.getStatus().equals(RequestStatus.REJECTED)) {

            for (Request request : requests) {
                request.setStatus(RequestStatus.REJECTED);
                rejected.add(request);
            }
        }

        event.setConfirmedRequests(confirmed.size());
        eventRepo.save(event);
        requestRepo.saveAll(requests);

        return new EventRequestStatusUpdateResult(
                requestMapper.requestToRequestDto(confirmed),
                requestMapper.requestToRequestDto(rejected));
    }

    @Override
    public List<EventFullDto> getEventsAdminFull(List<Long> users,
                                                 List<EventState> states,
                                                 List<Long> categories,
                                                 LocalDateTime rangeStart,
                                                 LocalDateTime rangeEnd,
                                                 Integer from,
                                                 Integer size) {

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Начало события не может быть позже окончания.");
        }

        Pageable pageable = CustomPageRequest.customOf(from, size);


        List<Event> events = eventRepo
                .findByAdminParameters(users, states, categories, rangeStart, rangeEnd, pageable).getContent();

        if (events.isEmpty()) return Collections.emptyList();

        Map<Long, Long> views = statsService.getViews(events);

        List<EventFullDto> fullDtos = new ArrayList<>();
        for (Event event : events) {
            fullDtos.add(eventMapper.eventToEventFullDto(event, views.getOrDefault(event.getId(), 0L)));

        }


        return fullDtos;
    }

    @Override
    public EventFullDto updateEventsAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        checkTime(updateEventAdminRequest.getEventDate(), 1);
        Event event = eventRepo.findById(eventId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Event", eventId)));
        log.info("Get eventeventeventeventevent: {} ", event);
        if ((event.getState() == EventState.PUBLISHED) || (event.getState() == EventState.REJECTED)) {
            throw new ConflictException(
                    "Нельзя менять опубликованное или отмененное.");
        }
        updateLocation(updateEventAdminRequest.getLocation(), event);
        updateCategory(updateEventAdminRequest.getCategory(), event);

        updateBasicData(
                event,
                updateEventAdminRequest.getAnnotation(),
                updateEventAdminRequest.getDescription(),
                updateEventAdminRequest.getPaid(),
                updateEventAdminRequest.getTitle(),
                updateEventAdminRequest.getRequestModeration(),
                updateEventAdminRequest.getParticipantLimit(),
                updateEventAdminRequest.getEventDate(),
                updateEventAdminRequest.getStateAction(), true);

        Event updatedEvent = eventRepo.save(event);

        long hits = getHits(eventId);

        return eventMapper.eventToEventFullDto(updatedEvent, hits);

    }

    private Event checkInitiator(Long userId, Long eventId) {
        userService.checkExistUserById(userId);
        return eventRepo.findByIdAndInitiatorId(eventId, userId).orElseThrow(() -> new NotFoundException(
                Constants.INITIATOR_NOT_FOUND));

    }

    private void checkTime(LocalDateTime eventDate, int hours) {
        LocalDateTime currentTime = LocalDateTime.now();

        if (eventDate != null && eventDate.isBefore(currentTime.plusHours(hours))) {
            throw new BadRequestException(
                    String.format(
                            "Время на которые намечено событие не может быть раньше, " +
                                    "чем через %d час(а) от текущего момента Value:%s", hours,
                            eventDate));
        }
    }

    private void updateCategory(Long categoryId, Event event) {
        if (categoryId != null) {
            final Category category = categoriesService.getCategoryById(categoryId);
            event.setCategory(category);
        }
    }

    private void updateLocation(LocationDto locationDto, Event event) {
        if (locationDto != null) {
            final Location location = locationService.getLocation(locationDto);
            event.setLocation(location);
        }
    }

    private void updateBasicData(Event event,
                                 String annotation,
                                 String description,
                                 Boolean paid,
                                 String title,
                                 Boolean requestModeration,
                                 Integer participantLimit,
                                 LocalDateTime eventDate,
                                 StateAction stateAction, boolean admin) {

        if (annotation != null) {
            event.setAnnotation(annotation);
        }
        if (description != null) {
            event.setDescription(description);
        }
        if (paid != null) {
            event.setPaid(paid);
        }
        if (title != null) {
            event.setTitle(title);
        }
        if (requestModeration != null) {
            event.setRequestModeration(requestModeration);
        }
        if (participantLimit != null) {
            event.setParticipantLimit(participantLimit);
        }

        if (eventDate != null) {
            event.setEventDate(eventDate);
        }

        if (stateAction != null) {
            if (!admin && stateAction.equals(StateAction.SEND_TO_REVIEW)) {
                event.setState(EventState.PENDING);
            }
            if (!admin && stateAction.equals(StateAction.CANCEL_REVIEW)) {
                event.setState(EventState.CANCELED);
            }
            if (admin && stateAction.equals(StateAction.PUBLISH_EVENT)) {
                event.setState(EventState.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            }
            if (admin && stateAction.equals(StateAction.REJECT_EVENT)) {
                event.setState(EventState.REJECTED);
            }
        }
    }

    private Long getHits(Long eventId) {
        List<ViewStatsDto> views = statsService.getViewStats(
                Constants.START_TIME,
                Constants.END_TIME,
                Collections.singletonList("/events/" + eventId),
                true);

        long hits = views.stream().findFirst().map(view -> Long.valueOf(view.getHits())).orElse(0L);
        return hits;
    }

}
