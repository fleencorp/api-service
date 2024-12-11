package com.fleencorp.base.validator;

import com.fleencorp.base.validator.impl.EnumValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EnumValidator.class)
public @interface OneOf {

  String[] allowedValues() default {};

  Class<? extends Enum<?>> enumClass() default DefaultEnum.class;

  boolean ignoreCase() default false;

  String message() default "must be one of {allowedValues} or valid {enumClass}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  static enum DefaultEnum {}
}


