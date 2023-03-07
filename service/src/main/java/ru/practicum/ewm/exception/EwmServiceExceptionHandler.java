package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
@Slf4j
public class EwmServiceExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiError> handleObjectNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return error("Объект не найден", HttpStatus.NOT_FOUND, e);
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            ConstraintViolationException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ApiError> handleBadRequestExceptions(Exception e) {
        log.error(e.getMessage());
        return error("Запрос составлен некорректно", HttpStatus.BAD_REQUEST, e);
    }

    @ExceptionHandler({
            ConflictException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ApiError> handleConflictExceptions(Exception e) {
        log.error(e.getMessage());
        return error("Не выполненены требования запрошенной операции", HttpStatus.CONFLICT, e);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleOthers(Throwable e) {
        log.error("Непредвиденная ошибка", e);
        return error("Непредвиденная ошибка", HttpStatus.INTERNAL_SERVER_ERROR, e);
    }

    private ResponseEntity<ApiError> error(String reason, HttpStatus status, Throwable e) {
        ApiError error = new ApiError();

        error.setReason(reason);
        error.setStatus(status);
        error.setMessage(e.getMessage());
        error.setErrors(List.of(e));
        error.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(error, status);
    }
}