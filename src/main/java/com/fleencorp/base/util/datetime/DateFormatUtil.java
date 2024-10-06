package com.fleencorp.base.util.datetime;

/**
 * Utility class for date and time format patterns.
 *
 * <p> This class defines static constants for various date and time format patterns
 * commonly used in applications for parsing, formatting, and displaying dates and times.</p>
 *
 * <p> These constants can be used to standardize date and time representations across
 * different parts of the application.</p>
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
public class DateFormatUtil {

  /**
   * Date-time format including seconds, following the ISO-8601 standard.
   * Example: 2024-10-05T14:30:00
   */
  public static final String DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss";

  /**
   * Date-only format, representing year, month, and day.
   * Example: 2024-10-05
   */
  public static final String DATE = "yyyy-MM-dd";

  /**
   * Time-only format, with an optional hour part.
   * Example: 14:30 or 30 (minutes past the hour)
   */
  public static final String TIME = "[H:]mm";

  /**
   * Date-time format with milliseconds and the timezone represented as 'Z' (UTC).
   * Example: 2024-10-05T14:30:00.123Z
   */
  public static final String DATE_TIME_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  /**
   * Date-time format with nanoseconds and the timezone represented as 'Z' (UTC).
   * Example: 2024-10-05T14:30:00.123456789Z
   */
  public static final String DATE_TIME_WITH_TIMEZONE_NANOSECOND = "yyyy-MM-dd'T'HH:mm:ss.nnnnnnn'Z'";

  /**
   * Date-time format excluding seconds, used when minute precision is sufficient.
   * Example: 2024-10-05T14:30
   */
  public static final String DATE_TIME_NO_SECONDS = "yyyy-MM-dd'T'HH:mm";

  /**
   * Date-time format excluding seconds and without the 'T' separator between date and time.
   * Example: 2024-10-05 14:30
   */
  public static final String DATE_TIME_NO_PT_SECONDS = "yyyy-MM-dd HH:mm";

  // New constants
  /**
   * Time format in 12-hour format with am/pm.
   * Example: 11:00am
   */
  public static final String TIME_12_HOUR_AM_PM = "h:mma";

  /**
   * Full date format with the full month name and year.
   * Example: September 20, 2024
   */
  public static final String FULL_DATE_WITH_SUFFIX = "MMMM d, yyyy";

}

