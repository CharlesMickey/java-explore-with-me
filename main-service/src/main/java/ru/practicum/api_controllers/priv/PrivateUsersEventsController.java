package ru.practicum.api_controllers.priv;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.dto.NewEventDto;
import ru.practicum.events.service.EventService;

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
    List<EventShortDto> getUserRequests(
            @PathVariable Long userId,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") int from,
            @Positive @RequestParam(value = "size", defaultValue = "10") int size) {

        log.info("Get request ../events, userId: {} ", userId);

        return eventService.getUserEvents(userId, from, size);

    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    EventFullDto createUserEvent(@PathVariable Long userId, @RequestBody NewEventDto newEventDto) {

        log.info("Post request ../events, userId: {}, newEventDto: {} ", userId, newEventDto);

        return eventService.createUserEvent(userId, newEventDto);

    }
}
