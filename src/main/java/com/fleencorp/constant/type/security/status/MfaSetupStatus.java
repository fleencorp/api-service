package com.fleencorp.constant.type.security.status;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum MfaSetupStatus implements ApiParameter {

  COMPLETE("Complete"),
  IN_PROGRESS("In progress");

  private final String value;

  MfaSetupStatus(String value) {
    this.value = value;
  }
}
