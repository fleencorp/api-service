package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.EnumOrdinalValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

import static com.fleencorp.base.util.EnumUtil.getValues;
import static java.util.Objects.nonNull;

public class EnumOrdinalValidValidator implements ConstraintValidator<EnumOrdinalValid, String> {

  private List<Long> acceptedValues;

  @Override
  public void initialize(EnumOrdinalValid constraintAnnotation) {
    acceptedValues = getValues(constraintAnnotation.enumClass());
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
    if (nonNull(value)) {
      try {
        Long number = Long.parseLong(value);
        return acceptedValues.contains(number);
      } catch (NumberFormatException ignored) {}

      return false;
    }
    return true;
  }

}
