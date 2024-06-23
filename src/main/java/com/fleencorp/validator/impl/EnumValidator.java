package com.fleencorp.validator.impl;

import com.fleencorp.validator.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * Validator class that checks if a given CharSequence matches one of the names of an enum.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class EnumValidator implements ConstraintValidator<ValidEnum, CharSequence> {

  private List<String> acceptedValues;


  /**
   * Initializes the validator with the list of accepted enum names for the specified enum class.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration.
   */
  @Override
  public void initialize(ValidEnum constraintAnnotation) {
    Enum<?>[] enumConstants = constraintAnnotation.enumClass().getEnumConstants();

    if (isNull(enumConstants) || enumConstants.length == 0) {
      acceptedValues = Collections.emptyList();
    } else {
      acceptedValues = Stream.of(enumConstants)
              .map(Enum::name)
              .collect(Collectors.toList());
    }
  }

  /**
   * Validates if the provided CharSequence matches one of the names of the enum.
   *
   * @param value    the CharSequence to validate.
   * @param context  the context in which the constraint is evaluated.
   * @return {@code true} if the value matches an enum name or {@code null}; {@code false} otherwise.
   */
  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    if (nonNull(value)) {
      return acceptedValues.contains(value.toString().toUpperCase());
    }
    return true;
  }

}
