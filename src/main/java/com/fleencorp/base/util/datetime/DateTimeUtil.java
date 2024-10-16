package com.fleencorp.base.util.datetime;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;

import static java.util.Objects.nonNull;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Slf4j
public class DateTimeUtil {

  public static Date asDate(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static Date asDate(LocalDateTime localDateTime) {
    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
  }

  public static LocalDate asLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }

  public static LocalDateTime asLocalDateTime(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
  }

  public static long toMilliseconds(LocalDateTime dateTime, String timezone) {
    if (dateTime != null) {
      ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.of(timezone));
      return zonedDateTime.toInstant().toEpochMilli();
    }
    return 0;
  }

  public static long toHours(Date date1, Date date2) {
    long diffInMillis = Math.abs(date2.getTime() - date1.getTime());
    return HOURS.convert(diffInMillis, MILLISECONDS);
  }

  /**
   * Converts a LocalDateTime object to milliseconds since the epoch.
   *
   * @param dateTime The LocalDateTime object to convert.
   * @return The number of milliseconds since January 1, 1970, 00:00:00 GMT represented by the given date-time,
   *         or 0 if the input is null.
   */
  public static long toMilliseconds(LocalDateTime dateTime) {
    if (nonNull(dateTime)) {
      return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
    return 0;
  }

  /**
   * Converts a duration into milliseconds from the current date and time.
   *
   * @param duration The duration to be converted.
   * @return The equivalent duration in milliseconds from the current date and time,
   *         or 0 if the duration is null.
   */
  public static long durationToMilliseconds(Duration duration) {
    if (nonNull(duration)) {
      LocalDateTime dateTime = LocalDateTime.now().plus(duration);
      return toMilliseconds(dateTime);
    }
    return 0;
  }

  /**
   * Converts the given time units into milliseconds.
   *
   * <p>This method takes the time values in seconds, minutes, hours, and days,
   * multiplies them with 1000 to convert them to milliseconds, and then multiplies
   * the non-zero time values together to get the total time in milliseconds.</p>
   *
   * @param seconds the number of seconds
   * @param minutes the number of minutes
   * @param hours the number of hours
   * @param days the number of days
   * @return the total time in milliseconds
   */
  public static Long getTimeInMillis(int seconds, int minutes, int hours, int days) {
    // List of time values in milliseconds
    List<Integer> times = List.of(1000, seconds, minutes, hours, days);

    // Filter non-zero time values and multiply them together
    return times
            .stream()
            .filter(time -> time > 0)
            .reduce(1, Math::multiplyExact)
            .longValue();
  }

  public static LocalDateTime addHoursFromNow(int hour) {
    return LocalDateTime.now().plusHours(hour);
  }

  /**
   * Adds a specified number of minutes to the current LocalDateTime.
   *
   * <p>This method calculates and returns a LocalDateTime object that represents
   * the current date and time plus the specified number of minutes.</p>
   *
   * @param minutes the number of minutes to add to the current LocalDateTime
   * @return LocalDateTime object representing the future time after adding minutes
   */
  public static LocalDateTime addMinutesFromNow(int minutes) {
    return LocalDateTime.now().plusMinutes(minutes);
  }

  public static LocalDateTime toLocalDateTime(String date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatUtil.DATE);
    return LocalDate.parse(date, formatter).atStartOfDay();
  }

  public static LocalDateTime getDefaultDateOfBirth() {
    return LocalDateTime.of(2000, 1, 1, 0, 0, 0);
  }

  public static LocalTime getWorkingHourStart() {
    return LocalTime.of(9, 0);
  }

  public static LocalTime getWorkingHourEnd() {
    return LocalTime.of(18, 0);
  }

  public static boolean validateWorkingHour(String time) {
    if (nonNull(time)) {
      try {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DateFormatUtil.TIME);
        LocalTime workingTime = LocalTime.parse(time, dtf);
        return !workingTime.isBefore(getWorkingHourStart()) && !workingTime.isAfter(getWorkingHourEnd());
      } catch (DateTimeParseException ex) {
        log.error(ex.getMessage(), ex);
      }
    }
    return false;
  }

  public static LocalTime toTime(String time) {
    if (nonNull(time)) {
      try {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DateFormatUtil.TIME);
        return LocalTime.parse(time, dtf);
      } catch (DateTimeParseException ex) {
        log.error(ex.getMessage(), ex);
      }
    }
    return null;
  }

  public static boolean isDateTimeValid(String dateTime, String pattern) {
    if (nonNull(dateTime)) {
      try {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        dtf.parse(dateTime);
        return true;
      } catch (DateTimeParseException ex) {
        log.error(ex.getMessage(), ex);
      }
    }
    return false;
  }

  public static boolean isDateOrTimeValid(String dateTime, String pattern) {
    return isDateTimeValid(dateTime, pattern);
  }

  public static boolean isDateFuture(String date, String pattern) {
    if (nonNull(date)) {
      try {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDate after = LocalDate.parse(date, dtf);
        return LocalDate.now().isBefore(after);
      } catch (DateTimeParseException ex) {
        log.error(ex.getMessage(), ex);
      }
      return false;
    }
    return true;
  }

  public static LocalDate toDate(String date) {
    if (nonNull(date)) {
      try {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DateFormatUtil.DATE);
        return LocalDate.parse(date, dtf);
      } catch (DateTimeParseException ex) {
        log.error(ex.getMessage(), ex);
      }
    }
    return null;
  }

  public static String asDateTimeWithNoSeconds(LocalDateTime dateTime) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DateFormatUtil.DATE_TIME_NO_PT_SECONDS);

    if (nonNull(dateTime)) {
      return dateTime.format(formatter) + " " + getTimePeriod(dateTime);
    }

    LocalDateTime now = LocalDateTime.now();
    return now.format(formatter) + " " + getTimePeriod(now);
  }

  private static String getTimePeriod(LocalDateTime dateTime) {
    return (dateTime.getHour() < 12) ? "AM" : "PM";
  }

}
