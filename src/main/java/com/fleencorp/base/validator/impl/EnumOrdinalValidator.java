package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.ValidEnumOrdinal;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

import static com.fleencorp.base.util.EnumUtil.getValues;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;

/**
 * Validator class that checks if a given string represents a valid ordinal value of an enum.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class EnumOrdinalValidator implements ConstraintValidator<ValidEnumOrdinal, String> {

  private List<Long> acceptedValues;

  /**
   * Initializes the validator with the list of accepted ordinal values for the specified enum class.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration.
   */
  @Override
  public void initialize(ValidEnumOrdinal constraintAnnotation) {
    acceptedValues = getValues(constraintAnnotation.enumClass());
  }

  /**
   * Validates if the provided string is a valid ordinal value of the enum.
   *
   * @param value    the string representation of the ordinal value to validate.
   * @param context  the context in which the constraint is evaluated.
   * @return {@code true} if the value is a valid ordinal value or {@code null}; {@code false} otherwise.
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (nonNull(value)) {
      try {
        Long number = parseLong(value);
        return acceptedValues.contains(number);
      } catch (NumberFormatException ignored) {}
      return false;
    }
    return true;
  }

}
