package ru.practicum.ewm.request;

import java.util.List;

public interface RequestService {
    List<ParticipationRequestDto> getByUser(long userId);

    ParticipationRequestDto request(long userId, long eventId);

    ParticipationRequestDto cancel(long userId, long requestId);
}
