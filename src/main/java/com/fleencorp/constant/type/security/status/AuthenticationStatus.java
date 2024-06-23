package com.fleencorp.constant.type.security.status;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum AuthenticationStatus implements ApiParameter {

  IN_PROGRESS("In Progress"),
  COMPLETED("Completed");

  private final String value;

  AuthenticationStatus(String value) {
    this.value = value;
  }
}
