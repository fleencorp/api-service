package com.fleencorp.validator;

import com.fleencorp.validator.impl.YearValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = YearValidator.class)
public @interface ValidYear {

  String message() default "Invalid year format. Please provide a valid year (YYYY)";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String pattern() default "^\\d{4}$";
}