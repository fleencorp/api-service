package com.fleencorp.base.converter.impl.common;

import com.fasterxml.jackson.databind.util.StdConverter;

public class ToSentenceCaseConverter extends StdConverter<String, String> {

  /**
   * Converts the input String to sentence case, where only the first letter is capitalized,
   * and the rest are in lowercase.
   *
   * @param value The String to be converted to sentence case.
   * @return The sentence case version of the input String.
   */
  @Override
  public String convert(final String value) {
    if (value == null) {
      return null;
    }
    return ToSentenceCaseConverter.toSentenceCase(value);
  }

  /**
   * Converts a given string to sentence case, where only the first letter of the string is capitalized,
   * and the rest are in lowercase.
   *
   * @param value the string to be converted
   * @return the converted string with only the first letter capitalized
   */
  public static String toSentenceCase(final String value) {
    if (value == null || value.isEmpty()) {
      return value;
    }

    String trimmedValue = value.trim();
    return Character.toUpperCase(trimmedValue.charAt(0)) + trimmedValue.substring(1);
  }
}

