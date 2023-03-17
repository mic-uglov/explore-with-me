package ru.practicum.ewm.user.service;

import ru.practicum.ewm.user.model.NewUserRequest;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.model.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> get(List<Long> ids, int from, int size);

    UserDto create(NewUserRequest newUserRequest);

    void delete(long userId);

    User getUser(long id);

    void checkExistence(long id);

    List<User> getUsers(List<Long> userIds);
}