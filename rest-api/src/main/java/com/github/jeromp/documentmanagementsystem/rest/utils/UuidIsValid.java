package com.github.jeromp.documentmanagementsystem.rest.utils;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = StringToUuidValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UuidIsValid {
    String message() default "Invalid uuid type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
