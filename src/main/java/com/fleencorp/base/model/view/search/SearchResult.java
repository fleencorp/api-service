package com.fleencorp.base.model.view.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResult<T> {

  @JsonProperty("page_no")
  private Integer pageNo;

  @JsonProperty("page_size")
  private Integer pageSize;

  @JsonProperty("total_entries")
  private Long totalEntries;

  @JsonProperty("total_pages")
  private Integer totalPages;

  @JsonProperty("is_last")
  private boolean isLast;

  @JsonProperty("is_first")
  private boolean isFirst;

  @JsonProperty("next_page_token")
  private String nextPageToken;

  @JsonProperty("prev_page_token")
  private String prevPageToken;

  @JsonProperty("has_value")
  public boolean hasValue() {
    return values != null && !values.isEmpty();
  }

  @JsonProperty("values")
  private Collection<T> values = new ArrayList<>();

  public void setPageTokens(String nextPageToken, String prevPageToken) {
    this.nextPageToken = nextPageToken;
    this.prevPageToken = prevPageToken;
    this.setFirstAndLastStatus();
  }

  public void setFirstAndLastStatus() {
    if (prevPageToken != null || nextPageToken != null) {
      if (this.nextPageToken == null) {
        this.isLast = true;
      }
      if (this.prevPageToken != null) {
        this.isFirst = false;
      }
    }
  }

  public static <T> SearchResult<T> empty() {
    return new SearchResult<>();
  }


  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public void setTotalEntries(Long totalEntries) {
    this.totalEntries = totalEntries;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public void setLast(boolean last) {
    isLast = last;
  }

  public void setFirst(boolean first) {
    isFirst = first;
  }

  public void setValues(Collection<T> values) {
    this.values = values;
  }
}
