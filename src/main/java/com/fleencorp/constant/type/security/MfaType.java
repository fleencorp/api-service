package com.fleencorp.constant.type.security;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum MfaType implements ApiParameter {

  PHONE("PHONE"),
  EMAIL("Email"),
  AUTHENTICATOR("Authenticator"),
  NONE("None");

  private final String value;

  MfaType(String value) {
    this.value = value;
  }
}
