package com.fleencorp.base.util;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The StringUtils contains implementations and methods for converting a number represented as a string to various type of numbers
 * for example an integer or whole number.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Slf4j
public class StringUtil {

  private StringUtil() {}

  /**
   * Constructs a full name string from the given first name and last name.
   * This method uses the overloaded getFullName method, setting the middle name to null.
   *
   * @param firstName the first name of the person
   * @param lastName the last name of the person
   * @return a concatenated full name with first and last name, with extra spaces trimmed
   */
  public static String getFullName(String firstName, String lastName) {
    return getFullName(firstName, lastName, null);
  }

  /**
   * Constructs a full name string from the given first name, last name, and middle name.
   *
   * @param firstName the first name of the person
   * @param lastName the last name of the person
   * @param middleName the middle name of the person
   * @return a concatenated full name, with extra spaces trimmed
   */
  public static String getFullName(String firstName, String lastName, String middleName) {
    return
      (Objects.toString(firstName, "") + " "
      + Objects.toString(lastName, "") + " "
      + Objects.toString(middleName, "")).trim();
  }

  /**
   * Converts a delimited string into a map of key-value pairs.
   * The input string is split into pairs based on the specified pair separator,
   * and each pair is further split into key and value based on the key-value separator.
   *
   * @param inputString the string containing the key-value pairs
   * @param keyValueSeparator the character separating keys and values within each pair
   * @param pairSeparator the character separating different pairs
   * @return a map containing the key-value pairs extracted from the input string
   */
  public static Map<String, String> strToMap(String inputString, char keyValueSeparator, char pairSeparator) {
    Map<String, String> map = new HashMap<>();
    // Split the input string into key-value pairs
    String[] pairs = inputString.split(String.valueOf(pairSeparator));

    for (String pair : pairs) {
      // Split each pair into key and value
      String[] keyValue = pair.split(String.valueOf(keyValueSeparator));
      if (keyValue.length == 2) {
        // Add the key-value pair to the map after trimming whitespace
        map.put(keyValue[0].trim(), keyValue[1].trim());
      }
    }

    return map;
  }

  /**
   * Converts a delimited string into a map.
   * This method uses '=' as the key-value separator and ';' as the pair separator.
   *
   * @param inputString the input string to convert
   * @return a map containing key-value pairs extracted from the input string
   */
  public static Map<String, String> strToMap(String inputString) {
    // Use the helper method with default separators '=' and ';'
    return strToMap(inputString, '=', ';');
  }
}
