package ru.practicum.api_controllers.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
@Slf4j
public class AdminUsersController {
    private final UserService userService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public UserDto createUser(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("Post request /admin/users, newUserRequest: {}", newUserRequest);

        return userService.createUser(newUserRequest);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {
        log.info("Delete request /admin/users, id: {}", userId);
        userService.deleteUser(userId);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsers(@RequestParam(value = "ids", required = false) List<Long> ids,
                         @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
                         @Positive @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("Get request /admin/users, id: {}", ids);

        return userService.getUsers(ids, from, size);
    }
}
