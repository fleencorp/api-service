package com.fleencorp.base.model.request.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fleencorp.base.constant.base.PagingConstant.*;
import static com.fleencorp.base.util.FleenUtil.areNotEmpty;
import static com.fleencorp.base.util.FleenUtil.createPageable;
import static com.fleencorp.base.util.datetime.DateFormatUtil.DATE;
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

  @JsonProperty("q")
  protected String q = "";

  @JsonProperty("page_no")
  protected Integer pageNo = DEFAULT_PAGE_NUMBER;

  @JsonProperty("next_page_token")
  protected String nextPageToken = null;

  @JsonProperty("previous_page_token")
  protected String previousPageToken = null;

  @JsonProperty("page_size")
  protected Integer pageSize = DEFAULT_PAGE_SIZE;

  @JsonProperty("sort_dir")
  protected String sortDir = DEFAULT_SORT_DIRECTION;

  @JsonProperty("sort_by")
  protected String sortBy = DEFAULT_SORT_BY;

  @JsonFormat(shape = STRING, pattern = DATE)
  @JsonProperty("start_date")
  protected LocalDate startDate;

  @JsonFormat(shape = STRING, pattern = DATE)
  @JsonProperty("end_date")
  protected LocalDate endDate;

  @JsonFormat(shape = STRING, pattern = DATE)
  @JsonProperty("before_date")
  protected LocalDate beforeDate;

  @JsonFormat(shape = STRING, pattern = DATE)
  @JsonProperty("after_date")
  protected LocalDate afterDate;

  @JsonIgnore
  private Pageable page;

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

  public LocalDateTime getStartDateTime() {
    return nonNull(startDate)
      ? startDate.atStartOfDay()
      : null;
  }

  public LocalDateTime getEndDateTime() {
    return nonNull(endDate)
      ? endDate.atStartOfDay()
      : null;
  }

  public String getQ() {
    return q;
  }

  public void setQ(String q) {
    this.q = q;
  }

  public Integer getPageNo() {
    return pageNo;
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
}