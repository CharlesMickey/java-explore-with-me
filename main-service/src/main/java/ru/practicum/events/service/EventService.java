package ru.practicum.events.service;

import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {

    List<EventShortDto> getPublicEvents(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Boolean onlyAvailable, String sort,
                                        Integer from, Integer size, HttpServletRequest request);

    EventFullDto getPublicEventById(Long id, HttpServletRequest request);

    List<EventShortDto> getUserEvents(Long userId, int from, int size);

    EventFullDto createUserEvent(Long userId, NewEventDto newEventDto);

    }