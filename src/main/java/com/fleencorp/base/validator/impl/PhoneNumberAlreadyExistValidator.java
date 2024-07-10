package com.fleencorp.base.validator.impl;

import com.fleencorp.base.service.PhoneService;
import com.fleencorp.base.validator.PhoneNumberAlreadyExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

/**
 * Validator for checking if a phone number already exists in the system using a {@link PhoneService}.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Component
public class PhoneNumberAlreadyExistValidator implements ConstraintValidator<PhoneNumberAlreadyExist, String> {

  private final PhoneService phoneService;

  /**
   * Constructs a new PhoneNumberExistsValidator with the provided PhoneService.
   *
   * @param phoneService the service used to check if a phone number exists
   */
  public PhoneNumberAlreadyExistValidator(PhoneService phoneService) {
    this.phoneService = phoneService;
  }

  /**
   * Initializes the validator.
   *
   * @param constraintAnnotation the annotation instance for PhoneNumberExist
   */
  @Override
  public void initialize(PhoneNumberAlreadyExist constraintAnnotation) {}

  /**
   * Validates the phone number.
   *
   * @param phoneNumber the phone number to validate
   * @param context     the validation context
   * @return true if the phone number is valid (not found in the system), false otherwise
   */
  @Override
  public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
    if (nonNull(phoneNumber)) {
      return !(phoneService.isPhoneNumberExist(phoneNumber));
    }
    return true;
  }
}
