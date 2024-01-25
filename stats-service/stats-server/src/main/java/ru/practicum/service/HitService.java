package ru.practicum.service;

import java.time.LocalDateTime;
import java.util.List;

import ru.practicum.EndpointHitDto;
import ru.practicum.ViewStatsDto;

public interface HitService {
    EndpointHitDto createHit(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> getViewStats(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            boolean isUniq
    );
}
