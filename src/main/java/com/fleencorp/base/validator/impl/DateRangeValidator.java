package com.fleencorp.base.validator.impl;


import com.fleencorp.base.validator.DateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

import static java.util.Objects.nonNull;

/**
 * Validator class to check if a date range is valid, i.e., the start date is not after the end date.
 *
 * <p>Works with any pair of the same comparable type — {@code Instant}, {@code LocalDate},
 * {@code LocalDateTime}, {@code OffsetDateTime} and so on. A pair it cannot compare (different
 * types, or a type with no order) is a misconfigured annotation and fails loudly: it used to cast
 * to {@code LocalDateTime}, swallow the failure and pass every range.</p>
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
    final Object start;
    final Object end;
    try {
      start = getFieldValue(object, startFieldName);
      end = getFieldValue(object, endFieldName);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new IllegalStateException("@DateRange names a field " + object.getClass().getSimpleName()
        + " does not have: " + startFieldName + " / " + endFieldName, e);
    }

    if (nonNull(start) && nonNull(end)) {
      final boolean isValid = compare(start, end) <= 0;
      if (!isValid) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(startFieldName)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
      }
      return isValid;
    }

    return true;
  }

  /**
   * Orders two values of the same comparable type.
   *
   * @throws IllegalStateException when the two cannot be compared
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  private int compare(final Object start, final Object end) {
    if (!(start instanceof Comparable) || start.getClass() != end.getClass()) {
      throw new IllegalStateException("@DateRange cannot compare " + startFieldName + " ("
        + start.getClass().getSimpleName() + ") with " + endFieldName + " (" + end.getClass().getSimpleName() + ")");
    }
    return ((Comparable) start).compareTo(end);
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
    final Class<?> clazz = object.getClass();
    try {
      // a DTO may inherit the two fields from a base request
      for (Class<?> type = clazz; type != null; type = type.getSuperclass()) {
        try {
          final Field field = type.getDeclaredField(fieldName);
          field.setAccessible(true);
          return field.get(object);
        } catch (NoSuchFieldException ignored) {
          // look in the superclass
        }
      }
      throw new NoSuchFieldException(fieldName);
    } catch (NoSuchFieldException e) {
      throw new NoSuchFieldException("Field '" + fieldName + "' does not exist on object of class " + clazz.getName());
    } catch (IllegalAccessException e) {
      throw new IllegalAccessException("Cannot access field '" + fieldName + "' on object of class " + clazz.getName());
    }
  }
}