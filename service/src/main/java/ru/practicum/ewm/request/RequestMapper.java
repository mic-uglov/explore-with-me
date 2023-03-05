package ru.practicum.ewm.request;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestMapper {
    public static ParticipationRequestDto toDto(Request request) {
        ParticipationRequestDto dto = new ParticipationRequestDto();

        dto.setId(request.getId());
        dto.setCreated(request.getCreated());
        dto.setEvent(request.getEvent().getId());
        dto.setRequester(request.getRequester().getId());
        dto.setStatus(request.getStatus());

        return dto;
    }
}