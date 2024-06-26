package com.fleencorp.base.validator;

import com.fleencorp.base.validator.impl.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidPhoneNumber {

  String message() default "Invalid phone number format.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
