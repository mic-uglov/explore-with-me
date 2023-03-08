package ru.practicum.ewm.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.validation.NotBlankOrNull;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static ru.practicum.ewm.config.Settings.DATE_TIME_PATTERN;

@Getter
@Setter
public abstract class UpdateEventRequest {
    @NotBlankOrNull
    @Size(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @NotBlankOrNull
    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;

    private Boolean requestModeration;

    @NotBlankOrNull
    @Size(min = 3, max = 120)
    private String title;
}