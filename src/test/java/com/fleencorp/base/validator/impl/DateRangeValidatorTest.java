package com.fleencorp.base.validator.impl;

import com.fleencorp.base.validator.DateRange;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateRangeValidatorTest {

  record InstantRange(Instant from, Instant to) {}
  record LocalDateTimeRange(LocalDateTime from, LocalDateTime to) {}
  record LocalDateRange(LocalDate from, LocalDate to) {}
  record MixedRange(Instant from, LocalDateTime to) {}

  static class BaseRange {
    protected Instant from;
    protected Instant to;
  }
  static class InheritedRange extends BaseRange {
    InheritedRange(final Instant from, final Instant to) {
      this.from = from;
      this.to = to;
    }
  }

  private static final Instant EARLY = Instant.parse("2026-10-01T00:00:00Z");
  private static final Instant LATE = Instant.parse("2026-10-03T00:00:00Z");

  @Test
  @DisplayName("Instants: a start after the end is refused — it used to pass every Instant range")
  void instants_are_compared() {
    assertTrue(validator().isValid(new InstantRange(EARLY, LATE), context()));
    assertTrue(validator().isValid(new InstantRange(EARLY, EARLY), context()));
    assertFalse(validator().isValid(new InstantRange(LATE, EARLY), context()));
  }

  @Test
  @DisplayName("LocalDateTime and LocalDate ranges are compared as before")
  void local_types_are_compared() {
    final LocalDateTime start = LocalDateTime.of(2026, 10, 1, 9, 0);
    assertFalse(validator().isValid(new LocalDateTimeRange(start.plusHours(1), start), context()));
    assertTrue(validator().isValid(new LocalDateRange(LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 3)), context()));
  }

  @Test
  @DisplayName("Fields inherited from a base request are found")
  void inherited_fields_are_found() {
    assertFalse(validator().isValid(new InheritedRange(LATE, EARLY), context()));
  }

  @Test
  @DisplayName("A missing end or start is not this constraint's to refuse")
  void a_missing_value_is_valid() {
    assertTrue(validator().isValid(new InstantRange(null, LATE), context()));
  }

  @Test
  @DisplayName("Two types that cannot be compared fail loudly instead of passing")
  void mismatched_types_fail_loudly() {
    assertThrows(IllegalStateException.class,
      () -> validator().isValid(new MixedRange(EARLY, LocalDateTime.now()), context()));
  }

  private static DateRangeValidator validator() {
    final DateRangeValidator validator = new DateRangeValidator();
    validator.initialize(new DateRange() {
      @Override public String message() { return "Start date should be before or equal to end date"; }
      @Override public Class<?>[] groups() { return new Class<?>[0]; }
      @Override @SuppressWarnings("unchecked") public Class<? extends Payload>[] payload() { return new Class[0]; }
      @Override public String start() { return "from"; }
      @Override public String end() { return "to"; }
      @Override public Class<? extends Annotation> annotationType() { return DateRange.class; }
    });
    return validator;
  }

  /** Accepts the violation-builder chain the validator walks on a refusal. */
  private static ConstraintValidatorContext context() {
    return (ConstraintValidatorContext) fluent(ConstraintValidatorContext.class);
  }

  private static Object fluent(final Class<?> type) {
    return Proxy.newProxyInstance(type.getClassLoader(), new Class<?>[] {type}, (proxy, method, args) ->
      method.getReturnType().isInterface() ? fluent(method.getReturnType()) : null);
  }
}
