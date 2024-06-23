package com.fleencorp.constant.type.security.status;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum ProfileVerificationStatus implements ApiParameter {

  PENDING("Pending"),
  IN_PROGRESS("In Progress"),
  DISAPPROVED("Disapproved"),
  APPROVED("Approved");

  private final String value;

  ProfileVerificationStatus(String value) {
    this.value = value;
  }
}
