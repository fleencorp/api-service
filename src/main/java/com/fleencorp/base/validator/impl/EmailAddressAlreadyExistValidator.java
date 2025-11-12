package com.fleencorp.base.validator.impl;

import com.fleencorp.base.service.EmailService;
import com.fleencorp.base.validator.EmailAddressAlreadyExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static java.util.Objects.nonNull;

/**
 * Validator class that checks if an email address already exists in the system.
 *
 * @author Yusuf Alamu Musa
 */
public class EmailAddressAlreadyExistValidator implements ConstraintValidator<EmailAddressAlreadyExist, String> {

  private final EmailService emailService;

  /**
   * Constructs an EmailAddressExistValidator with the specified EmailService.
   *
   * @param emailService the email service used to check for existing email addresses.
   */
  public EmailAddressAlreadyExistValidator(EmailService emailService) {
    this.emailService = emailService;
  }


  /**
   * Initializes the validator. This implementation is a no-op.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration.
   */
  @Override
  public void initialize(EmailAddressAlreadyExist constraintAnnotation) {}


  /**
   * Checks if the provided email address is valid, meaning it does not already exist in the system.
   *
   * @param emailAddress the email address to validate.
   * @param context the context in which the constraint is evaluated.
   * @return {@code true} if the email address does not exist or is null; {@code false} otherwise.
   */
  @Override
  public boolean isValid(String emailAddress, ConstraintValidatorContext context) {
    if (nonNull(emailAddress)) {
      return !(emailService.isEmailAddressExist(emailAddress));
    }
    return true;
  }
}

