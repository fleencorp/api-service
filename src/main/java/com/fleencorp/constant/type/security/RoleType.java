package com.fleencorp.constant.type.security;

import lombok.Getter;

@Getter
public enum RoleType {

  SUPER_ADMINISTRATOR("Super Administrator"),
  ADMINISTRATOR("Administrator"),
  REFRESH_TOKEN_USER("Refresh Token User"),
  PRE_VERIFIED_USER("Pre Verified User"),
  PRE_AUTHENTICATED_USER("Pre Authenticated User"),
  USER("User"),
  CONTRIBUTOR("Contributor"),
  RESET_PASSWORD_USER("Reset Password User");

  private final String value;

  RoleType(String value) {
        this.value = value;
    }
}
