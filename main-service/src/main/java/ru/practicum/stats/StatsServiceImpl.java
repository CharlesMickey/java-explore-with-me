package ru.practicum.stats;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.EndpointHitDto;
import ru.practicum.StatsClient;
import ru.practicum.ViewStatsDto;
import ru.practicum.events.model.Event;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.utils.Constants.END_TIME;
import static ru.practicum.utils.Constants.START_TIME;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsClient statsClient;
    @Value(value = "${app}")
    private String app;

    @Override
    public void createEndpointHit(HttpServletRequest request) {
        statsClient.createEndpointHit(EndpointHitDto.builder()
                .app(app)
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI()).timestamp(LocalDateTime.now()).build());

    }

    @Override
    public List<ViewStatsDto> getViewStats(LocalDateTime start,
                                           LocalDateTime end,
                                           List<String> uris,
                                           boolean isUniq) {

        ResponseEntity<Object> responseEntity = statsClient.getViewStats(start, end, uris,isUniq);

        List<ViewStatsDto> viewStatsList = extractViewStatsFromResponse(responseEntity);

        return viewStatsList;
    }

    @Override
    public Map<Long, Long> getViews(List<Event> events) {

        Map<Long, Long> views = new HashMap<>();

        if (events.size() == 0) {
            return views;
        }

        List<Event> publishedEvents = events.stream()
                .filter(event -> event.getPublishedOn() != null)
                .collect(Collectors.toList());

        List<String> uris = publishedEvents.stream()
                    .map(Event::getId)
                    .map(eventId -> "/events/" + eventId)
                    .collect(Collectors.toList());

        List<ViewStatsDto> hits = getViewStats(START_TIME, END_TIME, uris, false);

        for (ViewStatsDto hit : hits) {
                Long eventId = Long.parseLong(hit.getUri().split("/", 0)[2]);
                views.put(eventId, Long.valueOf(hit.getHits()));
            }

        return views;
    }


    private List<ViewStatsDto> extractViewStatsFromResponse(ResponseEntity<Object> response) {
        Object responseBody = response.getBody();

        if (responseBody instanceof String) {
            String jsonBody = (String) responseBody;

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<ViewStatsDto> viewStatsList = objectMapper.readValue(jsonBody,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, ViewStatsDto.class));

                return viewStatsList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return Collections.emptyList();
    }

}
