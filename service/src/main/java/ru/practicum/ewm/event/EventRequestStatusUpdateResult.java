package ru.practicum.ewm.event;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.ewm.request.ParticipationRequestDto;

import java.util.List;

@Getter
@Setter
public class EventRequestStatusUpdateResult {
    List<ParticipationRequestDto> confirmedRequests;
    List<ParticipationRequestDto> rejectedRequests;
}