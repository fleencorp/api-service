package com.fleencorp.base.validator;

import com.fleencorp.base.validator.impl.FutureDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FutureDateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FutureDate {

  String message() default "Date should be in the future";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String datePattern() default "yyyy-MM-dd";

  String dateTimePattern() default "yyyy-MM-dd'T'HH:mm:ss";

  boolean dateOnly() default false;
}
