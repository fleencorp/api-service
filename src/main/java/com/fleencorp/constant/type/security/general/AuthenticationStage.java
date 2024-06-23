package com.fleencorp.constant.type.security.general;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum AuthenticationStage implements ApiParameter {

  NONE("None"),
  PRE_VERIFICATION("Pre Verification"),
  MFA_OR_PRE_AUTHENTICATION("Multi Factor or Two FA or Pre Authentication");

  private final String value;

  AuthenticationStage(String value) {
    this.value = value;
  }
}
