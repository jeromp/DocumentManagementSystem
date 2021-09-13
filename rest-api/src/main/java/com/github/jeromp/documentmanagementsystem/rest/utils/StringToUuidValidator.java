package com.github.jeromp.documentmanagementsystem.rest.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringToUuidValidator implements ConstraintValidator<com.github.jeromp.documentmanagementsystem.rest.utils.UuidIsValid, String> {

    @Override
    public boolean isValid(String uuid, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (!uuid.matches("[0-9a-f]{8}-([0-9a-f]{4}-){3}[0-9a-f]{12}")) {
            context.buildConstraintViolationWithTemplate("UUID " + uuid + " is not valid").addConstraintViolation();
            return false;
        }
        return true;
    }
}
