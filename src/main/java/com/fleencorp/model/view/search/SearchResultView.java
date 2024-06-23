package com.fleencorp.model.view.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_ABSENT;

@Builder
@Getter
@Setter
@JsonInclude(NON_ABSENT)
public class SearchResultView {

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

  private List<?> values;

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

}
