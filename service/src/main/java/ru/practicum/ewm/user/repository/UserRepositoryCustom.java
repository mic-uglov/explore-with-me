package ru.practicum.ewm.user.repository;

import ru.practicum.ewm.user.model.User;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> get(List<Long> ids, int from, int size);
}