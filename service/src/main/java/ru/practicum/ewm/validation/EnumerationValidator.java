package ru.practicum.ewm.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.TreeSet;

public class EnumerationValidator implements ConstraintValidator<Enumeration, String> {
    private Set<String> values;

    @Override
    public void initialize(Enumeration constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        values = new TreeSet<>();
        //noinspection rawtypes
        for (Enum e : constraintAnnotation.value().getEnumConstants()) {
            values.add(e.toString());
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (!values.contains(value)) {
            context.disableDefaultConstraintViolation();
            String messageTemplate = context.getDefaultConstraintMessageTemplate();
            context.buildConstraintViolationWithTemplate(String.format(messageTemplate, value))
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}