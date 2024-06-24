package com.fleencorp.base.model.request.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fleencorp.base.constant.base.PagingConstant.*;
import static com.fleencorp.base.util.FleenUtil.createPageable;
import static com.fleencorp.base.util.datetime.DateFormatUtil.DATE;

/**
 * Represents a search request with pagination and sorting details.
 * This class encapsulates parameters for searching, pagination, and sorting in a request.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

  @JsonProperty("q")
  @Builder.Default
  protected String q = "";

  @JsonProperty("page_no")
  @Builder.Default
  protected Integer pageNo = DEFAULT_PAGE_NUMBER;

  @JsonProperty("next_page_token")
  @Builder.Default
  protected String nextPageToken = null;

  @JsonProperty("previous_page_token")
  @Builder.Default
  protected String previousPageToken = null;

  @JsonProperty("page_size")
  @Builder.Default
  protected Integer pageSize = DEFAULT_PAGE_SIZE;

  @JsonProperty("sort_dir")
  @Builder.Default
  protected String sortDir = DEFAULT_SORT_DIRECTION;

  @JsonProperty("sort_by")
  @Builder.Default
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

  /**
   * Converts the SearchRequest parameters into a Pageable object for pagination and sorting.
   */
  public void toPageable() {
    Pageable pageable = createPageable(pageNo, pageSize, sortBy, sortDir);
    this.setPage(pageable);
  }

  public void updatePageSize(Integer pageSize) {
    this.pageSize = pageSize;
    this.toPageable();
  }
}