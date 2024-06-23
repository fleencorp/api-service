package com.fleencorp.constant.type.security;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum TokenType implements ApiParameter {

  ACCESS_TOKEN("ACCESS_TOKEN"),
  REFRESH_TOKEN("REFRESH_TOKEN"),
  RESET_PASSWORD("RESET_PASSWORD_USER");

  private final String value;

  TokenType(String value) {
    this.value = value;
  }

}
