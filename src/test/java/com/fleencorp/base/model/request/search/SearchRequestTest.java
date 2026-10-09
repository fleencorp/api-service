package com.fleencorp.base.model.request.search;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SearchRequestTest {

  @Test
  @DisplayName("A range of 1 to 3 October includes 3 October, up to its last microsecond")
  void the_end_day_is_part_of_the_range() {
    final SearchRequest request = new SearchRequest();
    request.setStartDate(LocalDate.of(2026, 10, 1));
    request.setEndDate(LocalDate.of(2026, 10, 3));

    assertEquals(LocalDateTime.of(2026, 10, 1, 0, 0), request.getStartDateTime());
    assertEquals(LocalDateTime.of(2026, 10, 3, 23, 59, 59, 999_999_000), request.getEndDateTime());
  }

  @Test
  @DisplayName("Dates alone give a range of UTC days")
  void dates_give_utc_days() {
    final SearchRequest request = new SearchRequest();
    request.setStartDate(LocalDate.of(2026, 10, 1));
    request.setEndDate(LocalDate.of(2026, 10, 3));

    assertEquals(Instant.parse("2026-10-01T00:00:00Z"), request.getRangeStart());
    assertEquals(Instant.parse("2026-10-03T23:59:59.999999Z"), request.getRangeEnd());
  }

  @Test
  @DisplayName("Exact moments win over the dates — the viewer's own day, sent as instants")
  void instants_win_over_dates() {
    final SearchRequest request = new SearchRequest();
    request.setStartDate(LocalDate.of(2026, 10, 1));
    request.setEndDate(LocalDate.of(2026, 10, 3));
    request.setFrom(Instant.parse("2026-09-30T23:00:00Z"));
    request.setTo(Instant.parse("2026-10-03T22:59:59.999Z"));

    assertEquals(Instant.parse("2026-09-30T23:00:00Z"), request.getRangeStart());
    assertEquals(Instant.parse("2026-10-03T22:59:59.999Z"), request.getRangeEnd());
  }

  @Test
  @DisplayName("No dates and no moments: no range")
  void nothing_set() {
    final SearchRequest request = new SearchRequest();
    assertNull(request.getRangeStart());
    assertNull(request.getRangeEnd());
    assertNull(request.getEndDateTime());
  }
}
