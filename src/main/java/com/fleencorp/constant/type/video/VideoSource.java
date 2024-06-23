package com.fleencorp.constant.type.video;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

/**
 * This enum represents the source of a video.
 * It implements the ApiParameter interface.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Getter
public enum VideoSource implements ApiParameter {

  /**
   * Represents YouTube as the video source.
   */
  YOUTUBE("YouTube");

  /**
   * The value of the video source.
   */
  private final String value;

  /**
   * Constructs a new VideoSource with the given value.
   *
   * @param value The value of the video source.
   */
  VideoSource(String value) {
    this.value = value;
  }
}
