package com.fleencorp.constant.type.security;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum TokenClaimField implements ApiParameter {

  FIRST_NAME("firstName"),
  LAST_NAME("lastName"),
  EMAIL_ADDRESS("emailAddress"),
  PHONE_NUMBER("phoneNumber"),
  PROFILE_PHOTO("profilePhoto"),
  STATUS("status");

  private final String value;

  TokenClaimField(String value) {
    this.value = value;
  }
}
