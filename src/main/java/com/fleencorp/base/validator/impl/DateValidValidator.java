package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.DateValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.fleencorp.base.util.datetime.DateTimeUtil.isDateOrTimeValid;

/**
 * Validator class to check if a date string is valid according to a specified pattern.
 * This class implements {@link ConstraintValidator} and uses the {@link DateValid} annotation.
 *
 * @author Yusuf Alamu Mua
 * @version 1.0
 */
public class DateValidValidator implements ConstraintValidator<DateValid, String> {

  private String datePattern;

  /**
   * Initializes the validator with the date pattern specified in the {@link DateValid} annotation.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration
   */
  @Override
  public void initialize(DateValid constraintAnnotation) {
    datePattern = constraintAnnotation.pattern();
  }

  /**
   * Validates whether the given date string matches the specified date pattern.
   *
   * @param date the date string to validate
   * @param constraintValidatorContext context in which the constraint is evaluated
   * @return {@code true} if the date string is valid according to the pattern, {@code false} otherwise
   */
  @Override
  public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
    return isDateOrTimeValid(date, datePattern);
  }
}
