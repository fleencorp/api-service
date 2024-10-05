package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

/**
 * Validator class that checks if a given string is a valid email address based on a regex pattern.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private Pattern pattern;

  /**
   * Initializes the validator with the pattern defined in the annotation.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration.
   */
  @Override
  public void initialize(ValidEmail constraintAnnotation) {
    pattern = Pattern.compile(constraintAnnotation.pattern());
  }

  /**
   * Validates if the provided email address matches the defined regex pattern.
   *
   * @param email    the email address to validate.
   * @param context  the context in which the constraint is evaluated.
   * @return {@code true} if the email is valid or {@code null}; {@code false} otherwise.
   */
  @Override
  public boolean isValid(String email, ConstraintValidatorContext context) {
    if (nonNull(email)) {
      return pattern.matcher(email).matches();
    }
    return true;
  }
}
