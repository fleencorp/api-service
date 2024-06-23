package com.fleencorp.validator;

import com.fleencorp.validator.impl.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {

  String message() default "Invalid email address format.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String pattern() default "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}";
}
