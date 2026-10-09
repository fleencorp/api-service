package com.fleencorp.base.model.request.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fleencorp.base.constant.base.PagingConstant.*;
import static com.fleencorp.base.util.FleenUtil.areNotEmpty;
import static com.fleencorp.base.util.FleenUtil.createPageable;
import static com.fleencorp.base.util.datetime.DateFormatUtil.DATE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * Represents a search request with pagination and sorting details.
 * This class encapsulates parameters for searching, pagination, and sorting in a request.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

  private static final int MAX_QUERY_LENGTH = 200;

  @JsonProperty("q")
  protected String q = "";

  @JsonProperty("pageNo")
  protected Integer pageNo = DEFAULT_PAGE_NUMBER;

  @JsonProperty("nextPageToken")
  protected String nextPageToken = null;

  @JsonProperty("previousPageToken")
  protected String previousPageToken = null;

  @JsonProperty("pageSize")
  protected Integer pageSize = DEFAULT_PAGE_SIZE;

  @JsonProperty("sortDir")
  protected String sortDir = DEFAULT_SORT_DIRECTION;

  @JsonProperty("sortBy")
  protected String sortBy = DEFAULT_SORT_BY;

  @JsonFormat(shape = STRING, pattern = DATE)
  @JsonProperty("startDate")
  protected LocalDate startDate;

  @JsonFormat(shape = STRING, pattern = DATE)
  @JsonProperty("endDate")
  protected LocalDate endDate;

  /**
   * The start of a range as an exact moment ({@code 2026-10-01T00:00:00Z}, or with the viewer's
   * offset). Preferred over {@link #startDate}, a calendar date that can only be read in one zone.
   */
  @JsonProperty("from")
  protected Instant from;

  /** The end of a range as an exact moment, inclusive. Preferred over {@link #endDate}. */
  @JsonProperty("to")
  protected Instant to;

  @JsonFormat(shape = STRING, pattern = DATE)
  @JsonProperty("beforeDate")
  protected LocalDate beforeDate;

  @JsonFormat(shape = STRING, pattern = DATE)
  @JsonProperty("afterDate")
  protected LocalDate afterDate;

  @JsonIgnore
  private Pageable page;

  @DecimalMin(value = "-90.0", message = "{user.location.latitude.DecimalMin}")
  @DecimalMax(value = "90.0", message = "{user.location.latitude.DecimalMax}")
  @JsonProperty("latitude")
  protected Double latitude;

  @DecimalMin(value = "-180.0", message = "{user.location.longitude.DecimalMin}")
  @DecimalMax(value = "180.0", message = "{user.location.longitude.DecimalMax}")
  @JsonProperty("longitude")
  protected Double longitude;

  @JsonProperty("radius")
  protected Double radius = 0.0;

  @JsonProperty("lastCreatedOn")
  protected Instant lastCreatedOn;

  @JsonProperty("remoteFriendly")
  private boolean remoteFriendly;

  @JsonProperty("isGlobal")
  private boolean isGlobal;

  @JsonProperty("isNationwide")
  private boolean isNationwide;

  public void setPage(Pageable page) {
    this.page = page;
  }

  /**
   * Converts the SearchRequest parameters into a Pageable object for pagination and sorting.
   */
  public void toPageable() {
    Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
    setPage(pageable);
  }

  public void updatePageSize(Integer size) {
    pageSize = size;
    toPageable();
  }

  public boolean areAllDatesSet() {
    return areNotEmpty(startDate, endDate);
  }

  public boolean hasLatitudeAndLongitude() {
    return nonNull(latitude) && nonNull(longitude);
  }

  /**
   * The first moment of {@link #startDate}, as a wall time. Read as UTC by the backend (its JVM runs
   * in UTC), so a day is a UTC day; send {@link #from} for a day in the viewer's own zone.
   */
  public LocalDateTime getStartDateTime() {
    return nonNull(startDate)
      ? startDate.atStartOfDay()
      : null;
  }

  /**
   * The last moment of {@link #endDate}, as a wall time, so the end day is part of the range: a
   * range of 1 to 3 October includes 3 October. It is the last microsecond — the precision
   * Postgres keeps — so an inclusive {@code BETWEEN} stops short of the next day.
   */
  public LocalDateTime getEndDateTime() {
    return nonNull(endDate)
      ? endDate.plusDays(1).atStartOfDay().minusNanos(1_000)
      : null;
  }

  /**
   * The start of the range: {@link #from} when sent, otherwise the start of {@link #startDate} as
   * a UTC day; {@code null} when neither is set.
   */
  public Instant getRangeStart() {
    if (nonNull(from)) {
      return from;
    }
    final LocalDateTime start = getStartDateTime();
    return nonNull(start) ? start.toInstant(ZoneOffset.UTC) : null;
  }

  /**
   * The end of the range, inclusive: {@link #to} when sent, otherwise the end of {@link #endDate}
   * as a UTC day; {@code null} when neither is set.
   */
  public Instant getRangeEnd() {
    if (nonNull(to)) {
      return to;
    }
    final LocalDateTime end = getEndDateTime();
    return nonNull(end) ? end.toInstant(ZoneOffset.UTC) : null;
  }

  public Instant getDefaultLastCreatedOn() {
    return nonNull(lastCreatedOn) ? lastCreatedOn : Instant.now();
  }

  /**
   * Returns a normalized version of the search query.
   *
   * <p>If the query is {@code null}, this method returns an empty string. Otherwise,
   * it trims leading and trailing whitespace, collapses consecutive whitespace
   * characters into a single space, and truncates the result to
   * {@code MAX_QUERY_LENGTH} characters if necessary.
   *
   * @return the normalized query, or an empty string if the original query is {@code null}
   */
  public String getQ() {
    if (isNull(q)) {
      return "";
    }

    final String normalized = q.strip().replaceAll("\\s+", " ");

    return normalized.length() > MAX_QUERY_LENGTH
      ? normalized.substring(0, MAX_QUERY_LENGTH).strip()
      : normalized;
  }

  public void setQ(String q) {
    this.q = q;
  }

  public Integer getPageNo() {
    return nonNull(pageNo) && pageNo < 0 ? DEFAULT_PAGE_NUMBER : pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public String getNextPageToken() {
    return nextPageToken;
  }

  public void setNextPageToken(String nextPageToken) {
    this.nextPageToken = nextPageToken;
  }

  public String getPreviousPageToken() {
    return previousPageToken;
  }

  public void setPreviousPageToken(String previousPageToken) {
    this.previousPageToken = previousPageToken;
  }

  public Integer getPageSize() {
    return nonNull(pageSize) && pageSize > MAX_PAGE_SIZE ? DEFAULT_PAGE_SIZE : pageSize;
  }

  public Integer getOriginalPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public String getSortDir() {
    return sortDir;
  }

  public void setSortDir(String sortDir) {
    this.sortDir = sortDir;
  }

  public String getSortBy() {
    return sortBy;
  }

  public void setSortBy(String sortBy) {
    this.sortBy = sortBy;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public LocalDate getBeforeDate() {
    return beforeDate;
  }

  public void setBeforeDate(LocalDate beforeDate) {
    this.beforeDate = beforeDate;
  }

  public LocalDate getAfterDate() {
    return afterDate;
  }

  public void setAfterDate(LocalDate afterDate) {
    this.afterDate = afterDate;
  }

  public Pageable getPage() {
    return page;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Double getRadius() {
    return radius;
  }

  public void setRadius(Double radius) {
    this.radius = radius;
  }

  public void setLastCreatedOn(Instant lastCreatedOn) {
    this.lastCreatedOn = lastCreatedOn;
  }
}