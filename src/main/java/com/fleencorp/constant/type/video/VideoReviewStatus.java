package com.fleencorp.constant.type.video;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

/**
 * This enum represents the status of a video submission.
 * It implements the ApiParameter interface. Possible values include:
 * <pre>
 *   - APPROVED: The video submission has been approved.
 *   - DECLINED: The video submission has been declined.
 * </pre>
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Getter
public enum VideoReviewStatus implements ApiParameter {

  /**
   * The video submission has been approved.
   */
  APPROVED("Approved"),

  /**
   * The video submission has been declined.
   */
  DECLINED("Declined");

  /**
   * The value of the video submission status.
   */
  private final String value;

  /**
   * Constructs a new VideoSubmissionStatus with the given value.
   *
   * @param value The value of the video submission status.
   */
  VideoReviewStatus(String value) {
    this.value = value;
  }
}

