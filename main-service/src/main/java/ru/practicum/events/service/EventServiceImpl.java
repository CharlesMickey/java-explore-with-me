package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ViewStatsDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.repository.EventRepo;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.locations.model.Location;
import ru.practicum.locations.repository.LocationRepo;
import ru.practicum.requests.dto.ParticipationRequestDto;
import ru.practicum.stats.StatsService;
import ru.practicum.statuses.EventState;
import ru.practicum.statuses.SortStatus;
import ru.practicum.users.model.User;
import ru.practicum.users.service.UserService;
import ru.practicum.utils.Constants;
import ru.practicum.utils.customPageRequest.CustomPageRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;
    private final UserService userService;
    private final StatsService statsService;
    private final EventMapper eventMapper;
    private final LocationRepo locationRepo;

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

        Pageable pageable = sort != null && sort.equals(SortStatus.EVENT_DATE)
                ? CustomPageRequest.customOf(from, size, Sort.by("eventDate").descending())
                : CustomPageRequest.customOf(from, size);
        List<Event> events = eventRepo
                .findByParameters(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, pageable).getContent();

        if (events.isEmpty()) return Collections.emptyList();
        log.info("EVENTS: {}", events);

        Map<Long, Long> views = statsService.getViews(events);
        List<EventShortDto> shortDtos = eventMapper.eventToEventShortDto(events, views);


        statsService.createEndpointHit(request);

        if (sort != null && sort.equals(SortStatus.VIEWS)) {

            shortDtos.sort(Comparator.comparing(EventShortDto::getViews).reversed());
        }


        return shortDtos;
    }

    @Override
    public EventFullDto getPublicEventById(Long id, HttpServletRequest request) {

        Event event = eventRepo.findByIdAndState(id, EventState.PUBLISHED).orElseThrow(() -> new NotFoundException(
                String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "Event", id)));

        List<ViewStatsDto> views = statsService.getViewStats(
                event.getPublishedOn(),
                event.getEventDate(),
                Collections.singletonList(request.getRequestURI()),
                true);

        long hits = views.stream().findFirst().map(view -> Long.valueOf(view.getHits())).orElse(0L);

        statsService.createEndpointHit(request);

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
        Location location = locationRepo.findOne(newEventDto.getLocation())

        return null;
    }
}
