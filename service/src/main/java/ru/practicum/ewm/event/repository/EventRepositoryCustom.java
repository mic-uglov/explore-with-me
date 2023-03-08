package ru.practicum.ewm.event.repository;

import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.service.EventQueryParams;

import java.util.List;

public interface EventRepositoryCustom {
    List<Event> getByParams(EventQueryParams params);
}