package com.fleencorp.util;

/**
 * <p>Utility class providing arithmetic operations.</p>
 * <br/>
 *
 * <p>The ArithmeticUtil class contains static methods for various arithmetic operations, such as addition, subtraction, multiplication, and division.
 * It serves as a collection of common arithmetic functions.</p>
 * <p>Methods within this utility class facilitate performing mathematical operations on different numeric data types.</p>
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class ArithmeticUtil {

  /**
   * <p>Increments the provided value by 1 and returns the result.</p>
   * <br/>
   *
   * <p>This method takes a Float value as input and increments it by 1.0.
   * If the input value is null, the method returns 1.0 as the incremented value.
   * Otherwise, it adds 1.0 to the provided value and returns the result.</p>
   *
   * @param value The Float value to be incremented.
   * @return The incremented Float value.
   */
  public static Float addAndReturn(Float value) {
    if (value == null) {
      return 1.0f;
    } else {
      return value + 1.0f;
    }
  }
}
