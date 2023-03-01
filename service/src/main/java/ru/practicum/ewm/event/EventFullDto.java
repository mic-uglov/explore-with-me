package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.category.CategoryDto;
import ru.practicum.ewm.user.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventFullDto {
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    LocalDateTime createdOn;
    String description;
    LocalDateTime eventDate;
    Long id;
    UserShortDto initiator;
    Location location;
    Boolean paid;
    Integer participantLimit;
    LocalDateTime publishedOn;
    Boolean requestModeration;
    EventState state;
    String title;
    Integer views;
}