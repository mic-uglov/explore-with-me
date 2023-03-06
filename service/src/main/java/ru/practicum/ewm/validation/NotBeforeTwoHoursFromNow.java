package ru.practicum.ewm.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@SuppressWarnings("unused")
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotBeforeTwoHoursFromNowValidator.class)
@Documented

public @interface NotBeforeTwoHoursFromNow {
    String message() default "{ru.yandex.practicum.shareit.validation.NotBeforeTwoHoursFromNow.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}