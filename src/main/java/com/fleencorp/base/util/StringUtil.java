package com.fleencorp.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

/**
 * The StringUtils contains implementations and methods for converting a number represented as a string to various type of numbers
 * for example an integer or whole number.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class StringUtil {

  private static final Logger log = LoggerFactory.getLogger(StringUtil.class);

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
    if (nonNull(inputString)) {
      // Split the input string into key-value pairs
      String[] pairs = inputString.trim().split(String.valueOf(pairSeparator));

      for (String pair : pairs) {
        // Split each pair into key and value
        String[] keyValue = pair.split(String.valueOf(keyValueSeparator));
        if (keyValue.length == 2) {
          // Add the key-value pair to the map after trimming whitespace
          map.put(keyValue[0].trim(), keyValue[1].trim());
        }
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


  /**
   * Replaces the delimiter in the input string with the specified replacement.
   *
   * @param input       the original string to be transformed
   * @param delimiter   the delimiter to split the string
   * @param replacement the string to join the split parts
   * @return the transformed string
   */
  public static String replaceWith(final String input, String delimiter, String replacement) {
    if (input == null || delimiter == null || replacement == null) {
      throw new IllegalArgumentException("Input, delimiter, and replacement must not be null");
    }
    return String.join(replacement, input.split(delimiter));
  }

  /**
   * Checks if the given email address is valid using a standard email pattern.
   *
   * <p>This method validates an email address using a commonly accepted regular
   * expression pattern for email addresses.</p>
   *
   * @param emailAddress The email address to validate.
   * @return True if the email address matches the standard pattern, false otherwise.
   */
  public static boolean isValidEmail(String emailAddress) {
    return isValidEmail(emailAddress, "[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}");
  }

  /**
   * Checks if the given email address is valid against a specified pattern.
   *
   * <p>This method validates an email address using a regular expression pattern.
   * If both the email address and pattern are non-null, the pattern is compiled
   * and applied to the email address.</p>
   *
   * @param emailAddress The email address to validate.
   * @param emailValidPattern The regular expression pattern to use for validation.
   * @return True if the email address matches the pattern, false otherwise.
   */
  public static boolean isValidEmail(String emailAddress, String emailValidPattern) {
    if (nonNull(emailAddress) && nonNull(emailValidPattern)) {
      Pattern pattern = Pattern.compile(emailValidPattern);
      return pattern.matcher(emailAddress).matches();
    }
    return false;
  }
}
