package com.fleencorp.constant.type.security;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum VerificationMessageType implements ApiParameter {

  PROFILE_UPDATE("Profile Update"),
  PRE_VERIFICATION("Pre Verification"),
  FORGOT_PASSWORD("Forgot Password"),
  PRE_AUTHENTICATION("Pre Authentication"),
  MFA_SETUP("Multi Factor Authentication Setup");

  private final String value;

  VerificationMessageType(String value) {
    this.value = value;
  }
}
