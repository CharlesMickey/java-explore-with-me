package ru.practicum.stats;

import ru.practicum.ViewStatsDto;
import ru.practicum.events.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface StatsService {

    void createEndpointHit(HttpServletRequest request);

    List<ViewStatsDto> getViewStats(LocalDateTime start,
                                    LocalDateTime end,
                                    List<String> uris,
                                    boolean isUniq);

    Map<Long, Long> getViews(List<Event> events);

}
