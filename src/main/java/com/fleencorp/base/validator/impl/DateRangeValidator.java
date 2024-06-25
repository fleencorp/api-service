package com.fleencorp.base.validator.impl;


import com.fleencorp.base.validator.DateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

/**
 * Validator class to check if a date range is valid, i.e., the start date is not after the end date.
 * This class implements {@link ConstraintValidator} and uses the {@link DateRange} annotation.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

  private String startFieldName;
  private String endFieldName;
  private String message;

  /**
   * Initializes the validator by setting up the field names and the error message from the annotation.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration
   */
  @Override
  public void initialize(DateRange constraintAnnotation) {
    startFieldName = constraintAnnotation.start();
    endFieldName = constraintAnnotation.end();
    message = constraintAnnotation.message();
  }

  /**
   * Validates whether the start date is not after the end date.
   *
   * @param object  the object containing the fields to validate
   * @param context context in which the constraint is evaluated
   * @return {@code true} if the start date is not after the end date, {@code false} otherwise
   */
  @Override
  public boolean isValid(Object object, ConstraintValidatorContext context) {
    try {
      LocalDateTime startDateTime = (LocalDateTime) getFieldValue(object, startFieldName);
      LocalDateTime endDateTime = (LocalDateTime) getFieldValue(object, endFieldName);

      if (nonNull(startDateTime) && nonNull(endDateTime)) {
        boolean isValid = !startDateTime.isAfter(endDateTime);
        if (!isValid) {
          context.buildConstraintViolationWithTemplate(message)
                  .addPropertyNode(startFieldName)
                  .addConstraintViolation()
                  .disableDefaultConstraintViolation();
        }
        return isValid;
      }
    } catch (Exception ignored) {}

    return true;
  }

  /**
   * Retrieves the value of a field from an object using reflection.
   *
   * @param object    the object containing the field
   * @param fieldName the name of the field to retrieve the value from
   * @return the value of the field
   * @throws NoSuchFieldException   if the field does not exist
   * @throws IllegalAccessException if the field cannot be accessed
   */
  private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
    Class<?> clazz = object.getClass();
    try {
      Field field = clazz.getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(object);
    } catch (NoSuchFieldException e) {
      throw new NoSuchFieldException("Field '" + fieldName + "' does not exist on object of class " + clazz.getName());
    } catch (IllegalAccessException e) {
      throw new IllegalAccessException("Cannot access field '" + fieldName + "' on object of class " + clazz.getName());
    }
  }
}