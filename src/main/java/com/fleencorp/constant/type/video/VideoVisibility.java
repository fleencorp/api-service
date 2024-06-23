package com.fleencorp.constant.type.video;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

/**
 * This enum represents the visibility of a video.
 * It implements the ApiParameter interface.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Getter
public enum VideoVisibility implements ApiParameter {

  /**
   * Represents the video as publicly visible.
   */
  PUBLIC("Public"),

  /**
   * Represents the video as privately visible.
   */
  PRIVATE("Private"),

  /**
   * Represents the video as unlisted.
   */
  UNLISTED("Unlisted");

  /**
   * The value of the video visibility.
   */
  private final String value;

  /**
   * Constructs a new VideoVisibility with the given value.
   *
   * @param value The value of the video visibility.
   */
  VideoVisibility(String value) {
    this.value = value;
  }
}

