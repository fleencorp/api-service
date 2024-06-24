package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.FieldMatch;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;


/**
 * Validator class that checks if two fields in an object have the same value.
 *
 * @author Yusuf Alamu Musa
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

  private String firstFieldName;
  private String secondFieldName;
  private String message;

  /**
   * Initializes the validator with the field names and custom error message from the annotation.
   *
   * @param constraintAnnotation the annotation instance for a given constraint declaration.
   */
  @Override
  public void initialize(final FieldMatch constraintAnnotation) {
    firstFieldName = constraintAnnotation.first();
    secondFieldName = constraintAnnotation.second();
    message = constraintAnnotation.message();
  }

  /**
   * Validates if the two specified fields in the object have the same value.
   *
   * @param object  the object to validate.
   * @param context the context in which the constraint is evaluated.
   * @return {@code true} if the fields have the same value or are both {@code null}; {@code false} otherwise.
   */
  @Override
  public boolean isValid(final Object object, final ConstraintValidatorContext context) {
    boolean valid = true;
    try
    {
      final Object firstObj = getFieldValue(object, firstFieldName);
      final Object secondObj = getFieldValue(object, secondFieldName);
      valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
    }
    catch (Exception ignored) {}

    if (!valid) {
      context.buildConstraintViolationWithTemplate(message)
              .addPropertyNode(firstFieldName)
              .addConstraintViolation()
              .disableDefaultConstraintViolation();
    }
    return valid;
  }

  /**
   * Retrieves the value of a specified field from the object using reflection.
   *
   * @param object    the object from which to retrieve the field value.
   * @param fieldName the name of the field to retrieve.
   * @return the value of the specified field in the object.
   * @throws NoSuchFieldException   if the specified field does not exist in the object's class.
   * @throws IllegalAccessException if the field cannot be accessed.
   */
  private Object getFieldValue(Object object, String fieldName) throws NoSuchFieldException, IllegalAccessException {
    Class<?> clazz = object.getClass();
    try {
      Field field = clazz.getDeclaredField(fieldName);
      ReflectionUtils.makeAccessible(field);
      return field.get(object);
    } catch (NoSuchFieldException e) {
      throw new NoSuchFieldException("Field '" + fieldName + "' does not exist on object of class " + clazz.getName());
    } catch (IllegalAccessException e) {
      throw new IllegalAccessException("Cannot access field '" + fieldName + "' on object of class " + clazz.getName());
    }
  }
}
