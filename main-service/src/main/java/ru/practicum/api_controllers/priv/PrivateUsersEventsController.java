package ru.practicum.api_controllers.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.*;
import ru.practicum.events.service.EventService;
import ru.practicum.requests.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users/{userId}/events")
@Slf4j
public class PrivateUsersEventsController {

    private final EventService eventService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    List<EventShortDto> getUserEvents(@PathVariable Long userId,
                                      @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
                                      @Positive @RequestParam(value = "size", defaultValue = "10") int size) {

        log.info("Get request ../events, userId: {} ", userId);
        return eventService.getUserEvents(userId, from, size);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto createUserEvent(@PathVariable Long userId, @Valid @RequestBody NewEventDto newEventDto) {

        log.info("Post request ../events, userId: {}, newEventDto: {} ", userId, newEventDto);
        return eventService.createUserEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventFullDto getUserEventFull(@PathVariable @Positive Long userId, @PathVariable @Positive Long eventId) {

        log.info("Get request ../events full data, userId: {}, eventId: {} ", userId, eventId);
        return eventService.getUserEventFull(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    EventFullDto updateUserEvent(@PathVariable @Positive Long userId,
                                 @PathVariable @Positive Long eventId,
                                 @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {

        log.info("Patch request ../events full data, userId: {}, eventId: {}, updateEventUserRequest: {} ",
                userId, eventId, updateEventUserRequest);
        return eventService.updateUserEvent(userId, eventId, updateEventUserRequest);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    List<ParticipationRequestDto> getUserEventRequests(@PathVariable @Positive Long userId,
                                                       @PathVariable @Positive Long eventId) {

        log.info("Get request ../events/requests full data, userId: {}, eventId: {} ", userId, eventId);
        return eventService.getUserEventRequests(userId, eventId);
    }

    @PatchMapping("{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateUserEventRequest(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

        log.info("Patch request ../events/requests full data, userId: {}, eventId: {} ", userId, eventId);
        return eventService.updateUserEventRequest(userId, eventId, eventRequestStatusUpdateRequest);
    }
}