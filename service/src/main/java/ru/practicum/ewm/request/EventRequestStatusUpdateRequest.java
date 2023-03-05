package ru.practicum.ewm.request;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.request.RequestStatus;
import ru.practicum.ewm.validation.Enumeration;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateRequest {
    List<Long> requestIds;

    @Enumeration(value = RequestStatus.class, message = "Неизвестный статус запроса: %s")
    RequestStatus status;
}