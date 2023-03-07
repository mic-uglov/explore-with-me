package ru.practicum.ewm.event;

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