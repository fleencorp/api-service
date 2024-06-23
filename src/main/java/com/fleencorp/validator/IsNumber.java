package com.fleencorp.validator;

import com.fleencorp.validator.impl.IsNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = IsNumberValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface IsNumber {
  String message() default "Invalid number";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
