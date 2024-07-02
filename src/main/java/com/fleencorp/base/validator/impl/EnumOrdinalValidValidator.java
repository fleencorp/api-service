package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.EnumOrdinalValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

import static com.fleencorp.base.util.EnumUtil.getValues;
import static java.util.Objects.nonNull;

/**
 * Validator class that checks if a given String value is a valid ordinal value for a specified enum.
 * Implements the {@link ConstraintValidator} interface for the {@link EnumOrdinalValid} annotation.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class EnumOrdinalValidValidator implements ConstraintValidator<EnumOrdinalValid, String> {

  private List<Long> acceptedValues;


  /**
   * Initializes the validator by extracting the accepted ordinal values from the specified enum class.
   *
   * @param constraintAnnotation the annotation instance containing the enum class to validate against
   */
  @Override
  public void initialize(EnumOrdinalValid constraintAnnotation) {
    acceptedValues = getValues(constraintAnnotation.enumClass());
  }

  /**
   * Checks if the given String value is a valid ordinal value for the specified enum.
   *
   * <p>If the value is not null, the method attempts to parse it into a Long. If the parsing is successful,
   * it checks if the parsed value is in the list of accepted ordinal values. If the parsing fails or the value
   * is not in the list, the method returns false. If the value is null, the method returns true, indicating
   * that null values are considered valid.</p>
   *
   * @param value the String value to validate
   * @param constraintValidatorContext context in which the constraint is evaluated
   * @return true if the value is a valid ordinal value for the specified enum, false otherwise
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (nonNull(value)) {
      try {
        Long number = Long.parseLong(value);
        return acceptedValues.contains(number);
      } catch (NumberFormatException ignored) {}

      return false;
    }
    return true;
  }

}
