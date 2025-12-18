package com.fleencorp.base.util;

import java.util.Collection;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Utility class for exception handling and validation.
 *
 * <p>This class provides static methods to perform checks on objects and collections,
 * ensuring they are not null, and throws custom exceptions if they are.</p>
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class ExceptionUtil {

  /**
   * Checks if the given value is null and throws the provided exception if it is.
   *
   * @param <T>       the type of the value to check
   * @param value     the value to check for null
   * @param exception the supplier that provides the exception to throw if the value is null
   * @throws RuntimeException if the value is null and the exception supplier is non-null
   */
  public static <T> void checkIsNull(final T value, final Supplier<? extends RuntimeException> exception) {
    // Check if the exception supplier is non-null and the value is null
    if (nonNull(exception) && isNull(value)) {
      // Throw the exception provided by the supplier
      throw exception.get();
    }
  }

  /**
   * Checks if the given collection or any of its elements is null and throws the provided exception if any are null.
   *
   * @param collection the collection to check for null elements
   * @param exception  the supplier that provides the exception to throw if any element is null
   * @throws RuntimeException if the collection or any element in the collection is null and the exception supplier is non-null
   */
  public static void checkIsNullAny(final Collection<?> collection, final Supplier<? extends RuntimeException> exception) {
    // Return early if the exception supplier is null
    if (isNull(exception)) {
      return;
    }

    // Check if the collection itself is null and throw exception if so
    checkIsNull(collection, exception);
    // Iterate through each element in the collection
    for (final Object value: collection) {
      // Check if the element is null and throw exception if so
      checkIsNull(value, exception);
    }
  }

  /**
   * Checks whether the provided collection of objects contains any null value and throws the supplied
   * exception if a null is found. The method first returns immediately if the exception supplier itself
   * is null, since no meaningful exception can be generated. It then validates that the collection
   * varargs array is not null and throws the supplied exception if it is. After that, each element
   * within the collection is examined in sequence, and if any element is null, the same supplied
   * exception is thrown. This method is useful when multiple arguments need to be validated together
   * before proceeding with further processing.
   *
   * @param exception a supplier that produces the exception to be thrown when a null value is detected
   * @param collection a varargs set of objects to inspect for null values
   */
  public static void checkIsNullAny(final Supplier<? extends RuntimeException> exception, final Object...collection) {
    // Return early if the exception supplier is null
    if (isNull(exception)) {
      return;
    }

    // Check if the collection itself is null and throw exception if so
    checkIsNull(collection, exception);
    // Iterate through each element in the collection
    for (final Object value: collection) {
      // Check if the element is null and throw exception if so
      checkIsNull(value, exception);
    }
  }

  /**
   * Checks if the provided condition is true. If so, throws a specified exception.
   *
   * <p>This method validates a condition by checking if the boolean parameter {@code isTrue} is true.
   * If the condition is met (i.e., {@code isTrue} is true), it throws an exception provided by the
   * {@code exceptionSupplier}.</p>
   *
   * @param isTrue the condition to be checked; expected to be false to avoid throwing an exception
   * @param exceptionSupplier a supplier that provides the exception to be thrown if the condition is true
   * @throws RuntimeException if {@code isTrue} is true
   */
  public static void checkIsTrue(final boolean isTrue, final Supplier<? extends RuntimeException> exceptionSupplier) {
    if (isTrue) {
      throw exceptionSupplier.get();
    }
  }

  /**
   * Checks if the provided condition is false. If not, throws a specified exception.
   *
   * <p>This method validates a condition by checking if the boolean parameter {@code isFalse} is false.
   * If the condition is not met (i.e., {@code isFalse} is true), it throws an exception provided by the
   * {@code exceptionSupplier}.</p>
   *
   * @param isFalse the condition to be checked; expected to be false
   * @param exceptionSupplier a supplier that provides the exception to be thrown if the condition is not false
   * @throws RuntimeException if {@code isFalse} is not false
   */
  public static void checkIsFalse(final boolean isFalse, final Supplier<? extends RuntimeException> exceptionSupplier) {
    if (!isFalse) {
      throw exceptionSupplier.get();
    }
  }

}
