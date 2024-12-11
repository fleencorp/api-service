package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.OneOf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;

/**
 * Validator class that checks if a given CharSequence matches one of the names of an enum.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class OneOfValidator implements ConstraintValidator<OneOf, CharSequence> {

  private final List<String> acceptedValues = new ArrayList<>();
  private boolean ignoreCase;

  @Override
  public void initialize(OneOf constraintAnnotation) {
    ignoreCase = constraintAnnotation.ignoreCase();
    if (constraintAnnotation.enumClass() != null) {
      Enum<?>[] enumConstants = constraintAnnotation.enumClass().getEnumConstants();
      initializeAcceptedValues(enumConstants);
    }

    if (constraintAnnotation.allowedValues() != null) {
      initializeAcceptedValues(constraintAnnotation.allowedValues());
    }
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    if (nonNull(value)) {
      return checkIfValueTheSame(value.toString());
    }
    return true;
  }

  protected boolean checkIfValueTheSame(String value) {
    for (String acceptedValue : acceptedValues) {
      if (ignoreCase && acceptedValue.equalsIgnoreCase(value)) {
        return true;
      } else if (acceptedValue.equals(value)) {
        return true;
      }
    }
    return false;
  }

  protected void initializeAcceptedValues(Enum<?>... enumConstants) {
    if (nonNull(enumConstants)) {
      acceptedValues.addAll(Stream.of(enumConstants).map(Enum::name).toList());
    }
  }

  protected void initializeAcceptedValues(String... values) {
    if (nonNull(values)) {
      acceptedValues.addAll(Stream.of(values).map(String::trim).toList());
    }
  }
}
