package ru.practicum.api_controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.UpdateEventAdminRequest;
import ru.practicum.events.service.EventService;
import ru.practicum.statuses.EventState;
import ru.practicum.utils.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/events")
@Slf4j
public class AdminEventsController {

    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEventsAdminFull(
            @RequestParam(required = false) List<Long> users,
            @RequestParam(required = false) List<EventState> states,
            @RequestParam(required = false) List<Long> categories,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATE_FORMAT) LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATE_FORMAT) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Get request admin/events"
                        + " with params: users='{}', categories={}, states={}, rangeStart={}, rangeEnd={}, "
                        + " onlyAvailable={}, from={}, size={}",
                users, categories, states, rangeStart, rangeEnd, from, size);

        return eventService.getEventsAdminFull(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEventsAdmin(@PathVariable Long eventId,
                                          @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("Patch request admin/events with params: updateEventAdminRequest={}", updateEventAdminRequest);
        return eventService.updateEventsAdmin(eventId, updateEventAdminRequest);
    }

}
