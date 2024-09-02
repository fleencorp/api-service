package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.FutureDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
  private String dateTimePattern;
  private boolean dateOnly;

  /**
   * Initializes the validator. This method is a placeholder for any initialization logic,
   * which is not required in this case.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration
   */
  @Override
  public void initialize(FutureDate constraintAnnotation) {
    datePattern = constraintAnnotation.datePattern();
    dateTimePattern = constraintAnnotation.dateTimePattern();
    dateOnly = constraintAnnotation.dateOnly();
  }

  /**
   * Validates whether a given date string is valid according to the configured date or date-time pattern.
   * This method delegates the validation to the {@link #validate(String)} method. If the date string is
   * invalid or cannot be parsed, it returns {@code false}. If the date string is {@code null}, it returns
   * {@code true} as it is considered valid.
   *
   * @param date    The date string to be validated.
   * @param context The context in which the constraint is evaluated (used by the validation framework).
   * @return {@code true} if the date string is valid or {@code null};
   *         {@code false} if the date string is invalid or cannot be parsed.
   * @throws DateTimeParseException if the date string cannot be parsed according to the configured pattern.
   */
  public boolean isValid(String date, ConstraintValidatorContext context) {
    if (nonNull(date)) {
      try {
        return validate(date);
      } catch (DateTimeParseException ignored) {}
      return false;
    }
    return true;
  }

  /**
   * Validates whether a given date string is valid based on the configured date or date-time pattern.
   * This method delegates the validation to the {@link #isValidDate(String, String)}} and {@link #isValidDateTime(String, String)}} method, using the appropriate
   * pattern based on the {@code dateOnly} flag.
   *
   * @param date The date string to be validated.
   * @return {@code true} if the date string is valid according to the configured pattern;
   *         {@code false} otherwise.
   */
  public boolean validate(String date) {
    if (dateOnly) {
      return isValidDate(date, datePattern);
    } else {
      return isValidDateTime(date, dateTimePattern);
    }
  }

  /**
   * Validates whether a given date string matches the specified pattern and checks
   * if the date is in the future compared to the current date.
   *
   * @param date    The date string to be validated.
   * @param pattern The pattern against which the date string should be validated.
   * @return {@code true} if the date is valid and is in the future compared to the current date;
   *         {@code false} otherwise.
   * @throws DateTimeParseException if the date string cannot be parsed according to the specified pattern.
   */
  private boolean isValidDate(String date, String pattern) {
    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
    LocalDate after = LocalDate.parse(date, dtf);
    return LocalDate.now().isBefore(after);
  }

  private boolean isValidDateTime(String date, String pattern) {
    final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
    LocalDateTime after = LocalDateTime.parse(date, dtf);
    return LocalDateTime.now().isBefore(after);
  }
}
