package com.fleencorp.constant.type.security.status;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum MemberStatus implements ApiParameter {

  ACTIVE("Active"),
  INACTIVE("Inactive"),
  DISABLED("Disabled"),
  BANNED("Banned");

  private final String value;

  MemberStatus(String value) {
    this.value = value;
  }
}
