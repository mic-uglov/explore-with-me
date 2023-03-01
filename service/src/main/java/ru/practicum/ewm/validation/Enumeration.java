package ru.practicum.ewm.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@SuppressWarnings("unused")
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumerationValidator.class)
@Documented

public @interface Enumeration {
    String message() default "{ru.yandex.practicum.shareit.validation.Enumeration.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> value();
}