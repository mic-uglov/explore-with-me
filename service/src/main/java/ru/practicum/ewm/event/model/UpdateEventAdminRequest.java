package ru.practicum.ewm.event.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventAdminRequest extends UpdateEventRequest {
    private EventAdminStateAction stateAction;
}