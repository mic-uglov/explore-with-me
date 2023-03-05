package ru.practicum.ewm.request;

import lombok.experimental.UtilityClass;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public static EventRequestStatusUpdateResult toResult(Map<RequestStatus, List<Request>> requests) {
        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();

        result.setConfirmedRequests(toDtos(requests.get(RequestStatus.CONFIRMED)));
        result.setRejectedRequests(toDtos(requests.get(RequestStatus.REJECTED)));

        return result;
    }

    private static List<ParticipationRequestDto> toDtos(@Nullable List<Request> requests) {
        if (requests == null) {
            return null;
        }

        return requests.stream().map(RequestMapper::toDto).collect(Collectors.toUnmodifiableList());
    }
}