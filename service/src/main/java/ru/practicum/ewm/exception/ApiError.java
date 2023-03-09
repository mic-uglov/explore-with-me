package ru.practicum.ewm.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static ru.practicum.ewm.config.Settings.DATE_TIME_PATTERN;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    @JsonIgnore
    private List<Object> errors;

    private String message;

    private String reason;

    private HttpStatus status;

    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime timestamp;
}