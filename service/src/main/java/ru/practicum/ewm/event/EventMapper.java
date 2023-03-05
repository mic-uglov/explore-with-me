package ru.practicum.ewm.event;

import ru.practicum.ewm.category.Category;
import ru.practicum.ewm.category.CategoryMapper;
import ru.practicum.ewm.user.User;
import ru.practicum.ewm.user.UserMapper;

public class EventMapper {
    public static EventFullDto toDto(Event event) {
        EventFullDto dto = new EventFullDto();

        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(CategoryMapper.toDto(event.getCategory()));
        dto.setDescription(event.getDescription());
        dto.setEventDate(event.getEventDate());
        dto.setLocation(event.getLocation());
        dto.setPaid(event.getPaid());
        dto.setParticipantLimit(event.getParticipantLimit());
        dto.setRequestModeration(event.getRequestModeration());
        dto.setTitle(event.getTitle());
        dto.setCreatedOn(event.getCreatedOn());
        dto.setInitiator(UserMapper.toShortDto(event.getInitiator()));
        dto.setPublishedOn(event.getPublishedOn());
        dto.setState(event.getState());

        dto.setConfirmedRequests(event.getConfirmedRequests());
        dto.setViews(event.getViews());

        return dto;
    }

    public static Event toEvent(
            NewEventDto dto, Category category, User initiator) {
        Event event = new Event(category, initiator);

        event.setAnnotation(dto.getAnnotation());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setLocation(dto.getLocation());
        event.setPaid(dto.getPaid() != null && dto.getPaid());
        event.setParticipantLimit(dto.getParticipantLimit() == null ? 0 : dto.getParticipantLimit());
        event.setRequestModeration(dto.getRequestModeration() != null && dto.getRequestModeration());
        event.setTitle(dto.getTitle());

        return event;
    }

    public static EventShortDto toShortDto(Event event) {
        EventShortDto dto = new EventShortDto();

        dto.setId(event.getId());
        dto.setAnnotation(event.getAnnotation());
        dto.setCategory(CategoryMapper.toDto(event.getCategory()));
        dto.setEventDate(event.getEventDate());
        dto.setInitiator(UserMapper.toShortDto(event.getInitiator()));
        dto.setPaid(event.getPaid());
        dto.setTitle(event.getTitle());
        dto.setConfirmedRequests(event.getConfirmedRequests());
        dto.setViews(event.getViews());

        return dto;
    }
}