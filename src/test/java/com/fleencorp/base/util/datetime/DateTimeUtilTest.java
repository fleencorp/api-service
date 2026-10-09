package com.fleencorp.base.util.datetime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeUtilTest {

  private static final ZoneId LAGOS = ZoneId.of("Africa/Lagos");

  @Test
  @DisplayName("The zone overloads read and write in the zone given, not the JVM's")
  void zone_overloads_use_the_zone_given() {
    final Date midnightInLagos = DateTimeUtil.asDate(LocalDate.of(2026, 10, 9), LAGOS);
    assertEquals(java.time.Instant.parse("2026-10-08T23:00:00Z"), midnightInLagos.toInstant());

    assertEquals(LocalDate.of(2026, 10, 9), DateTimeUtil.asLocalDate(midnightInLagos, LAGOS));
    assertEquals(LocalDate.of(2026, 10, 8), DateTimeUtil.asLocalDate(midnightInLagos, ZoneId.of("UTC")));
    assertEquals(LocalDateTime.of(2026, 10, 9, 0, 0), DateTimeUtil.asLocalDateTime(midnightInLagos, LAGOS));
    assertEquals(midnightInLagos, DateTimeUtil.asDate(LocalDateTime.of(2026, 10, 9, 0, 0), LAGOS));
  }
}
