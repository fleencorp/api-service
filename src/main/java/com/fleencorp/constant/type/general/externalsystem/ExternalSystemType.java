package com.fleencorp.constant.type.general.externalsystem;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

/**
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Getter
public enum ExternalSystemType implements ApiParameter {

  GOOGLE_RECAPTCHA("Google ReCaptcha"),
  YOUTUBE_API("YouTube API");

  private final String value;

  ExternalSystemType(String value) {
    this.value = value;
  }
}
