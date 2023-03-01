package ru.practicum.ewm.user;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> get(List<Long> ids, int from, int size);
}