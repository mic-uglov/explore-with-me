package ru.practicum.ewm.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ApiError {
    List<Object> errors;
    String message;
    String reason;
    HttpStatus status;
    LocalDateTime timestamp;
}