package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEventAdminRequest extends UpdateEventRequest {
    private EventAdminStateAction stateAction;
}