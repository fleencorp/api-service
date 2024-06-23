package com.fleencorp.constant.type.video;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

/**
 * This enum represents the status of a video.
 * It implements the ApiParameter interface. Possible values include:
 * <pre>
 *   - IN_REVIEW: Video is currently under review.
 *   - APPROVED: Video has been approved.
 *   - DISAPPROVED: Video has been disapproved.
 *  </pre>
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Getter
public enum VideoStatus implements ApiParameter {

  /**
   * Video is currently in draft.
   */
  DRAFT("Draft"),

  /**
   * Video is currently under review.
   */
  IN_REVIEW("In Review"),

  /**
   * Video has been approved.
   */
  APPROVED("Approved"),

  /**
   * Video has been disapproved.
   */
  DISAPPROVED("Disapproved");

  /**
   * The value of the video status.
   */
  private final String value;

  /**
   * Constructs a new VideoStatus with the given value.
   *
   * @param value The value of the video status.
   */
  VideoStatus(String value) {
    this.value = value;
  }
}