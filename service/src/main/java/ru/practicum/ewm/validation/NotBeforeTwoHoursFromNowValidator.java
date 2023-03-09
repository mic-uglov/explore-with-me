package ru.practicum.ewm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class NotBeforeTwoHoursFromNowValidator
        implements ConstraintValidator<NotBeforeTwoHoursFromNow, LocalDateTime> {
    public static boolean isValid(LocalDateTime value) {
        return value == null || !value.isBefore(LocalDateTime.now().plusHours(2));
    }

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return isValid(value);
    }
}