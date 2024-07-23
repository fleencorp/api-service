package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.ValidEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Validator class that checks if a given CharSequence matches one of the names of an enum.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 *
 * @see <a href="https://velog.io/@hellozin/Annotation%EC%9C%BC%EB%A1%9C-Enum-%EA%B2%80%EC%A6%9D%ED%95%98%EA%B8%B0">
 *   Validating Enums with Annotations</a>
 */
public class EnumValidator implements ConstraintValidator<ValidEnum, CharSequence> {

  private List<String> acceptedValues;
  private boolean ignoreCase;

  /**
   * Initializes the validator with the list of accepted enum names for the specified enum class.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration.
   */
  @Override
  public void initialize(ValidEnum constraintAnnotation) {
    ignoreCase = constraintAnnotation.ignoreCase();
    Enum<?>[] enumConstants = constraintAnnotation.enumClass().getEnumConstants();
    initializeAcceptedValues(enumConstants);
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
      return checkIfValueTheSame(acceptedValues, value.toString());
    }
    return true;
  }

  /**
   * Checks if the provided value matches any in the list of accepted values.
   *
   * @param acceptedValues the list of accepted values
   * @param value the value to check
   * @return {@code true} if the value matches any accepted value; {@code false} otherwise
   */
  protected boolean checkIfValueTheSame(List<String> acceptedValues, String value) {
    for (String acceptedValue : acceptedValues) {
      if (ignoreCase && acceptedValue.equalsIgnoreCase(value)) {
        return true;
      } else if (acceptedValue.equals(value)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Initializes accepted values based on provided enum constants.
   *
   * @param enumConstants the enum constants to initialize accepted values from
   */
  protected void initializeAcceptedValues(Enum<?>... enumConstants) {
    if (isNull(enumConstants) || enumConstants.length == 0) {
      acceptedValues = Collections.emptyList();
    } else {
      acceptedValues = Stream.of(enumConstants)
          .map(Enum::name)
          .collect(Collectors.toList());
    }
  }

}
