package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.FutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Objects.nonNull;


/**
 * Validator class to check if a date string represents a future date.
 * This class implements {@link ConstraintValidator} and uses the {@link FutureDate} annotation.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class FutureDateValidator implements ConstraintValidator<FutureDate, String> {

  private String datePattern;

  /**
   * Initializes the validator. This method is a placeholder for any initialization logic,
   * which is not required in this case.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration
   */
  @Override
  public void initialize(FutureDate constraintAnnotation) {
    datePattern = constraintAnnotation.pattern();
  }

  /**
   * Validates whether the given date string represents a date in the future.
   *
   * @param date the date string to validate
   * @param context context in which the constraint is evaluated
   * @return {@code true} if the date string represents a future date, {@code false} otherwise
   */
  public boolean isValid(String date, ConstraintValidatorContext context) {
    if (nonNull(date)) {
      try {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(datePattern);
        LocalDate after = LocalDate.parse(date, dtf);
        return LocalDate.now().isBefore(after);
      } catch (DateTimeParseException ignored) {}
      return false;
    }
    return true;
  }
}
