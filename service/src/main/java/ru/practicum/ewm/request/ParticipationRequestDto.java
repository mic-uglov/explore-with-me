package ru.practicum.ewm.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static ru.practicum.ewm.config.Settings.DATE_TIME_PATTERN;

@Getter
@Setter
public class ParticipationRequestDto {
    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime created;
    private Long event;
    private Long id;
    private Long requester;
    private RequestStatus status;
}