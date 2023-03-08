package ru.practicum.ewm.request.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.request.model.ParticipationRequestDto;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}