package com.fleencorp.validator;

import com.fleencorp.validator.impl.BooleanValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = BooleanValidator.class)
public @interface ValidBoolean {

  String message() default "Invalid boolean string value. Please provide 'true' or 'false'.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}