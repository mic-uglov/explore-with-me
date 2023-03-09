package ru.practicum.ewm.stats.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static ru.practicum.ewm.stats.common.Settings.DATE_TIME_PATTERN;

@Getter
@Setter
public class EndpointHitDto {
    private Long id;

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotNull
    private String ip;

    @JsonFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime timestamp;
}