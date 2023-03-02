package ru.practicum.ewm.event;

import java.util.List;

public interface EventRepositoryCustom {
    List<Event> getByParams(EventQueryParams params);
}