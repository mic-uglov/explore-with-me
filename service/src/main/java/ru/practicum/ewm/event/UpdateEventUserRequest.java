package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventUserRequest extends UpdateEventRequest {
    private EventUserStateAction stateAction;
}