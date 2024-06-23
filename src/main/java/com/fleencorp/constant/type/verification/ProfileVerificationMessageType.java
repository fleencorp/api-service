package com.fleencorp.constant.type.verification;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum ProfileVerificationMessageType implements ApiParameter {

  PENDING("Pending"),
  IN_PROGRESS("In Progress"),
  DISAPPROVED("Disapproved"),
  APPROVED("Approved"),
  SIGNUP_COMPLETE("Sign Up Complete");

  private final String value;

  ProfileVerificationMessageType(String value) {
    this.value = value;
  }
}
