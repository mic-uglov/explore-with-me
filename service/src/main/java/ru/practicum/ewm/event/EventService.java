package ru.practicum.ewm.event;

import java.util.List;

public interface EventService {
    EventFullDto create(long userId, NewEventDto newEventDto);

    List<EventShortDto> search(EventQueryParams params);

    List<EventFullDto> adminSearch(EventQueryParams params);
}