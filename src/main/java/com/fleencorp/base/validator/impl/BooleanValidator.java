package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.ValidBoolean;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.fleencorp.base.util.FleenUtil.isBoolean;
import static java.util.Objects.nonNull;

/**
 * Validator class that checks if a given string is a valid boolean value.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class BooleanValidator implements ConstraintValidator<ValidBoolean, Object> {

  /**
   * Initializes the validator. This implementation is a no-op.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration.
   */
  @Override
  public void initialize(ValidBoolean constraintAnnotation) {}

  /**
   * Checks if the provided string value is a valid boolean ("true" or "false").
   *
   * @param value the string value to validate.
   * @param context the context in which the constraint is evaluated.
   * @return {@code true} if the string is a valid boolean or is null; {@code false} otherwise.
   */
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if (nonNull(value)) {
      return isBoolean(value);
    }
    return true;
  }
}
