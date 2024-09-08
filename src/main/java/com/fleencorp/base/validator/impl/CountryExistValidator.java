package com.fleencorp.base.validator.impl;

import com.fleencorp.base.service.BasicCountryService;
import com.fleencorp.base.validator.CountryExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

/**
* Validator class that checks if a given country ID exists using the {@link BasicCountryService}.
* Implements the {@link ConstraintValidator} interface for the {@link CountryExist} annotation.
*
* @author Yusuf Alamu Musa
* @version 1.0
*/
@Component
public class CountryExistValidator implements ConstraintValidator<CountryExist, String> {

  private final BasicCountryService service;

  /**
  * Constructs a new CountryExistValidator with the specified {@link BasicCountryService}.
  *
  * @param service the CountryService to use for checking if a country exists
  */
  public CountryExistValidator(final BasicCountryService service) {
    this.service = service;
  }

  /**
  * Initializes the validator. This method is a no-op for this validator.
  *
  * @param constraintAnnotation the annotation instance for a given constraint declaration
  */
  @Override
  public void initialize(final CountryExist constraintAnnotation) {}

  /**
  * Checks if the given country code is valid by verifying its existence using the {@link BasicCountryService}.
  *
  * <p>If the country code is not null, the method attempts to parse it into a Long and checks if the country
  * exists using the CountryService. If the parsing fails or the country does not exist, the method returns false.
  * If the country code is null, the method returns true, indicating that null values are considered valid.</p>
  *
  * @param countryCode the country code to validate
  * @param context context in which the constraint is evaluated
  * @return true if the country code exists, false otherwise
  */
  @Override
  public boolean isValid(final String countryCode, final ConstraintValidatorContext context) {
    if (nonNull(countryCode)) {
      try {
        return service.isCountryExists(countryCode);
      } catch (final Exception ignored) {}
      return false;
    }
    return true;
  }
}
