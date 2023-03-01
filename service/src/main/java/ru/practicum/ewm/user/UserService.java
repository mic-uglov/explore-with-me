package ru.practicum.ewm.user;

import java.util.List;

public interface UserService {
    List<UserDto> get(List<Long> ids, int from, int size);

    UserDto create(NewUserRequest newUserRequest);

    void delete(long userId);
}