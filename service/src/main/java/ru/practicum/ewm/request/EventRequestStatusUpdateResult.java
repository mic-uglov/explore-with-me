package ru.practicum.ewm.request;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.request.ParticipationRequestDto;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateResult {
    private List<ParticipationRequestDto> confirmedRequests;
    private List<ParticipationRequestDto> rejectedRequests;
}