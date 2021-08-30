package com.github.jeromp.documentmanagementsystem.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringToUuidValidator implements ConstraintValidator<IsUuidValid, String> {
    private static final String UUID_PATTERN = "[0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}";

    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (!uuid.matches(UUID_PATTERN)) {
            context.buildConstraintViolationWithTemplate("UUID " + uuid + " is not valid").addConstraintViolation();
            return false;
        }
        return true;
    }
}
