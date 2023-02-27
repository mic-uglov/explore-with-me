package ru.practicum.ewm.stats.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "^((25[0-5]|(2[0-4]|1[0-9]|[1-9]|)[0-9])(\\.(?!$)|$)){4}$")
    private String ip;

    @JsonFormat(pattern=DATE_TIME_PATTERN)
    private LocalDateTime timestamp;
}