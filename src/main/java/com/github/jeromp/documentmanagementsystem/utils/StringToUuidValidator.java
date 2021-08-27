package com.github.jeromp.documentmanagementsystem.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToUuidValidator implements ConstraintValidator<UuidIsValid, String> {

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
