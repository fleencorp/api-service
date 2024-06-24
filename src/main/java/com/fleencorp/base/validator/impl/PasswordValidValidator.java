package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;

import static java.util.Objects.nonNull;
import static org.passay.EnglishCharacterData.*;

/**
 * Validator class that checks if a given password meets specified validation rules.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class PasswordValidValidator implements ConstraintValidator<ValidPassword, String> {

  /**
   * Initializes the validator.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration.
   */
  @Override
  public void initialize(ValidPassword constraintAnnotation) { }

  /**
   * Validates if the given password meets specified validation rules.
   *
   * @param password                   the password to validate.
   * @param constraintValidatorContext the context in which the constraint is evaluated.
   * @return {@code true} if the password meets the validation rules; {@code false} otherwise.
   */
  @Override
  public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
    return validatePassword(password);
  }

  /**
   * Validates the password against specified rules.
   *
   * @param password the password to validate.
   * @return {@code true} if the password meets the validation rules; {@code false} otherwise.
   */
  public boolean validatePassword(String password) {
    if (nonNull(password)) {
      PasswordValidator validator = new PasswordValidator(null, Arrays.asList(
        new LengthRule(8, 24),                  // Length between 8 and 24 characters
        new CharacterRule(UpperCase, 1),   // At least one uppercase character
        new CharacterRule(LowerCase, 1),   // At least one lowercase character
        new CharacterRule(Digit, 1),       // At least one digit
        new CharacterRule(Special, 1),     // At least one special character
        new WhitespaceRule()));                 // No whitespace allowed

      RuleResult result = validator.validate(new PasswordData(password));
      return result.isValid();
    }
    return false;
  }
}
