package com.fleencorp.constant.type.security;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum ProfileType implements ApiParameter {

  CONTRIBUTOR("Contributor"),
  USER("User"),
  ADMIN("Admin");

  private final String value;

  ProfileType(String value) {
    this.value = value;
  }
}
