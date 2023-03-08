package ru.practicum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.category.model.CategoryDto;
import ru.practicum.ewm.user.model.UserShortDto;

import java.time.LocalDateTime;

import static ru.practicum.ewm.config.Settings.DATE_TIME_PATTERN;

@Getter
@Setter
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;
    private Long id;
    private UserShortDto initiator;
    private Location location;
    private boolean paid;
    private int participantLimit;
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private EventState state;
    private String title;
    private Long views;
}