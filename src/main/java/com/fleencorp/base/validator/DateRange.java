package com.fleencorp.base.validator;


import com.fleencorp.base.validator.impl.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRange {

  String message() default "Start date should  be before or equal to end date";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String start();

  String end();

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @interface List {

    DateRange[] value();
  }
}
