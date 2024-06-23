package com.fleencorp.validator;

import com.fleencorp.validator.impl.PhoneNumberExistsValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PhoneNumberExistsValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface PhoneNumberExist {

  String message() default "Phone Number already exists";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
