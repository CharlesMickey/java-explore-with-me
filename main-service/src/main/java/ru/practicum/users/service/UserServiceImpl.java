package ru.practicum.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.NotFoundException;
import ru.practicum.users.dto.NewUserRequest;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.mapper.UserMapper;
import ru.practicum.users.model.User;
import ru.practicum.users.repository.UserRepo;
import ru.practicum.utils.Constants;
import ru.practicum.utils.customPageRequest.CustomPageRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserDto createUser(NewUserRequest newUserRequest) {
        User user = userRepo.save(userMapper.newUserToUser(newUserRequest));
        return userMapper.userToUserDto(user);
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        if (!userRepo.existsById(userId)) {
            throw new NotFoundException(
                    String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "User", userId));
        }
        userRepo.deleteById(userId);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {

        Pageable pageable = CustomPageRequest.customOf(from, size);
        final List<User> users = (ids == null || ids.isEmpty())
                ? userRepo.findAll(pageable).getContent()
                : userRepo.findAllByIdIn(ids, pageable).getContent();

        return userMapper.userToUserDto(users);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepo.findById(userId).orElseThrow(() -> new NotFoundException(
                String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "User", userId)));
    }

    @Override
    public void checkExistUserById(Long userId) {
        if (!userRepo.existsById(userId)) {
            throw new NotFoundException(
                    String.format(Constants.WITH_ID_D_WAS_NOT_FOUND, "User", userId));
        }
    }
}
