package com.fleencorp.base.validator;

import com.fleencorp.base.validator.impl.IsNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = IsNumberValidator.class)
public @interface IsNumber {
  String message() default "Invalid number";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
