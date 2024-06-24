package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.ValidYear;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static java.util.Objects.nonNull;


/**
 * Validator for checking if a string represents a valid year in YYYY format.
 */
public class YearValidator implements ConstraintValidator<ValidYear, String> {

  private Pattern pattern;

  /**
   * Initializes the validator.
   *
   * @param constraintAnnotation the annotation instance for ValidYear
   */
  @Override
  public void initialize(ValidYear constraintAnnotation) {
    this.pattern = Pattern.compile(constraintAnnotation.pattern());
  }


  /**
   * Validates if the given string is a valid year in YYYY format.
   *
   * @param value   the string to validate
   * @param context the validation context
   * @return true if the string is a valid year (YYYY format), false otherwise
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (nonNull(value)) {
      return pattern.matcher(value).matches();
    }
    return true;
  }
}