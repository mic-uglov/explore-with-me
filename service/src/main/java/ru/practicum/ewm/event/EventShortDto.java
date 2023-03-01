package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.category.CategoryDto;
import ru.practicum.ewm.user.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventShortDto {
    String annotation;
    CategoryDto category;
    Integer confirmedRequests;
    LocalDateTime eventDate;
    Long id;
    UserShortDto initiator;
    Boolean paid;
    String title;
    Integer views;
}