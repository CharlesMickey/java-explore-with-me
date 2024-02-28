package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ViewStatsDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.mapper.EventMapper;
import ru.practicum.events.model.Event;
import ru.practicum.events.repository.EventRepo;
import ru.practicum.exception.BadRequestException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.stats.StatsService;
import ru.practicum.statuses.EventState;
import ru.practicum.statuses.SortStatus;
import ru.practicum.utils.Constants;
import ru.practicum.utils.customPageRequest.CustomPageRequest;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepo eventRepo;
    private final StatsService statsService;
    private final EventMapper eventMapper;

    @Override
    public List<EventShortDto> getPublicEvents(String text,
                                               List<Long> categories,
                                               Boolean paid,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               Boolean onlyAvailable,
                                               Sort sort,
                                               Integer from,
                                               Integer size,
                                               HttpServletRequest request) {

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Начало события не может быть позже окончания.");
        }

        Pageable pageable = sort != null && sort.equals(SortStatus.EVENT_DATE)
                ? CustomPageRequest.customOf(from, size, sort)
                : CustomPageRequest.customOf(from, size);


        statsService.createEndpointHit(request);

        return null;
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
}
