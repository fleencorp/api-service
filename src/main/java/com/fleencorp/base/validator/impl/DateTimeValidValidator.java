package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.DateTimeValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.fleencorp.base.util.datetime.DateTimeUtil.isDateTimeValid;

/**
 * Validator class to check if a date-time string is valid according to a specified pattern.
 * This class implements {@link ConstraintValidator} and uses the {@link DateTimeValid} annotation.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class DateTimeValidValidator implements ConstraintValidator<DateTimeValid, String> {

  private String dateTimePattern;


  /**
   * Initializes the validator with the date-time pattern specified in the {@link DateTimeValid} annotation.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration
   */
  @Override
  public void initialize(DateTimeValid constraintAnnotation) {
    dateTimePattern = constraintAnnotation.pattern();
  }

  /**
   * Validates whether the given date-time string matches the specified date-time pattern.
   *
   * @param dateTime the date-time string to validate
   * @param context context in which the constraint is evaluated
   * @return {@code true} if the date-time string is valid according to the pattern, {@code false} otherwise
   */
  @Override
  public boolean isValid(String dateTime, ConstraintValidatorContext context) {
    return isDateTimeValid(dateTime, dateTimePattern);
  }
}
