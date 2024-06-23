package com.fleencorp.validator;

import com.fleencorp.validator.impl.EmailAddressExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = EmailAddressExistValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface EmailAddressExist {

  String message() default "Email Address already exists";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
