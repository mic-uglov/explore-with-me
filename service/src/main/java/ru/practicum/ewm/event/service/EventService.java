package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.model.EventShortDto;
import ru.practicum.ewm.event.model.NewEventDto;
import ru.practicum.ewm.event.model.UpdateEventAdminRequest;
import ru.practicum.ewm.event.model.UpdateEventUserRequest;
import ru.practicum.ewm.event.model.EventFullDto;
import ru.practicum.ewm.event.model.Event;

import java.util.List;

public interface EventService {
    EventFullDto create(long userId, NewEventDto newEventDto);

    List<EventShortDto> search(EventQueryParams params);

    List<EventFullDto> adminSearch(EventQueryParams params);

    EventFullDto adminUpdate(long eventId, UpdateEventAdminRequest request);

    EventFullDto publicGet(long id);

    EventFullDto getEventOfUser(long userId, long eventId);

    EventFullDto userUpdate(long userId, long eventId, UpdateEventUserRequest request);

    Event getEvent(long eventId);

    List<Event> getEvents(List<Long> eventIds);
}