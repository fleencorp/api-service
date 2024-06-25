package com.fleencorp.base.validator;

import com.fleencorp.base.validator.impl.DateTimeValidValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateTimeValidValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeValid {

  String message() default "Date and time is invalid";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String pattern() default "yyyy-MM-dd'T'HH:mm:ss";
}
