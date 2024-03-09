package ru.practicum.users.service;

import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.model.User;

import java.util.List;

public interface UserService {

    UserDto createUser(NewUserRequest newUserRequest);

    void deleteUser(long userId);

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    User findUserById(Long userId);

    void checkExistUserById(Long userId);
}
