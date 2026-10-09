package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.FutureDate;
import jakarta.validation.Payload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FutureDateValidatorTest {

  /** 15:00 UTC — 16:00 in Lagos. */
  private static final Clock NOW = Clock.fixed(Instant.parse("2026-10-09T15:00:00Z"), ZoneOffset.UTC);

  @Test
  @DisplayName("A value with its zone is compared as that exact moment")
  void a_zoned_value_is_compared_as_a_moment() {
    final FutureDateValidator validator = validator(false);
    assertTrue(validator.isValid("2026-10-09T15:00:01Z", null));
    assertFalse(validator.isValid("2026-10-09T14:59:59Z", null));
    // 15:30 in Lagos is 14:30 UTC — already past, though 15:30 reads later than 15:00
    assertFalse(validator.isValid("2026-10-09T15:30:00+01:00", null));
    assertTrue(validator.isValid("2026-10-09T16:30:00+01:00", null));
  }

  @Test
  @DisplayName("A zone-less value is compared with now in the JVM's zone, as before")
  void a_zone_less_value_uses_the_clock_zone() {
    assertTrue(validator(false).isValid("2026-10-09T15:30:00", null));
    assertFalse(validator(false).isValid("2026-10-09T14:30:00", null));
    assertTrue(validator(true).isValid("2026-10-10", null));
    assertFalse(validator(true).isValid("2026-10-09", null));
  }

  @Test
  @DisplayName("Nothing is valid; something unreadable is not")
  void null_and_unreadable() {
    assertTrue(validator(false).isValid(null, null));
    assertFalse(validator(false).isValid("next tuesday", null));
  }

  private static FutureDateValidator validator(final boolean dateOnly) {
    final FutureDateValidator validator = new FutureDateValidator(NOW);
    validator.initialize(new FutureDate() {
      @Override public String message() { return "Date should be in the future"; }
      @Override public Class<?>[] groups() { return new Class<?>[0]; }
      @Override @SuppressWarnings("unchecked") public Class<? extends Payload>[] payload() { return new Class[0]; }
      @Override public String datePattern() { return "yyyy-MM-dd"; }
      @Override public String dateTimePattern() { return "yyyy-MM-dd'T'HH:mm:ss"; }
      @Override public boolean dateOnly() { return dateOnly; }
      @Override public Class<? extends Annotation> annotationType() { return FutureDate.class; }
    });
    return validator;
  }
}
