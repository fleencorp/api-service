package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.ValidPhoneNumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import static java.util.Objects.nonNull;

/**
 * Validator for checking if a phone number is valid according to the E.164 standard using Google's libphonenumber library.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

  /**
   * Initializes the validator.
   *
   * @param constraintAnnotation the annotation instance for ValidPhoneNumber
   */
  @Override
  public void initialize(ValidPhoneNumber constraintAnnotation) {}

  /**
   * Validates the phone number.
   *
   * @param number  the phone number to validate
   * @param context the validation context
   * @return true if the phone number is valid according to E.164 standard, false otherwise
   */
  @Override
  public boolean isValid(String number, ConstraintValidatorContext context) {
    if (nonNull(number)) {
      PhoneNumberUtil util = PhoneNumberUtil.getInstance();
      PhoneNumber phoneNumber;
      try {
        phoneNumber = util.parse(number, null);
        return util.isValidNumber(phoneNumber);
      } catch (NumberParseException ignored) {
      }
      return false;
    }
    return true;
  }

}
