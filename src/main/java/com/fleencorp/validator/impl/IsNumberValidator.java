package com.fleencorp.validator.impl;

import com.fleencorp.validator.IsNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.math.NumberUtils.isParsable;

/**
 * Validator class that checks if a given string represents a valid number.
 *
 * @author Yusuf Alamu Musa
 */
public class IsNumberValidator implements ConstraintValidator<IsNumber, String> {

  /**
   * Initializes the validator.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration.
   */
  @Override 
  public void initialize(IsNumber constraintAnnotation) {}

  /**
   * Validates if the given string represents a valid number.
   *
   * @param number  the string to validate.
   * @param context the context in which the constraint is evaluated.
   * @return {@code true} if the string is a valid number; {@code false} otherwise.
   */
  @Override
  public boolean isValid(String number, ConstraintValidatorContext context) {
    if (nonNull(number)) {
      try {
        return isParsable(number);
      } catch (NumberFormatException ex) {
      }
      return false;
    }
    return true;
  }
}
