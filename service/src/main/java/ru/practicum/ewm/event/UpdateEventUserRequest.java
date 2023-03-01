package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.validation.Enumeration;
import ru.practicum.ewm.validation.NotBlankOrNull;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateEventUserRequest {
    @NotBlankOrNull
    @Size(min = 20, max = 2000)
    String annotation;

    Long category;

    @NotBlankOrNull
    @Size(min = 20, max = 7000)
    String description;

    LocalDateTime eventDate;

    Location location;

    Boolean paid;

    Integer participantLimit;

    Boolean requestModeration;

    @Enumeration(value = EventStateAction.class, message = "Неизвестное действие: %s")
    EventStateAction stateAction;

    @NotBlankOrNull
    @Size(min = 3, max = 120)
    String title;
}