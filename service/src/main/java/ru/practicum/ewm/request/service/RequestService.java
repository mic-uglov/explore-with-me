package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.model.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.model.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.model.ParticipationRequestDto;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getByUser(long userId);

    ParticipationRequestDto request(long userId, long eventId);

    ParticipationRequestDto cancel(long userId, long requestId);

    List<ParticipationRequestDto> getRequests(long userId, long eventId);

    EventRequestStatusUpdateResult updateStatus(
            long userId, long eventId, EventRequestStatusUpdateRequest request);
}