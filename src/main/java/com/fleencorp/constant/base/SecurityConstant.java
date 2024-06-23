package com.fleencorp.constant.base;

import static com.fleencorp.util.datetime.DateTimeUtil.getTimeInMillis;

public final class SecurityConstant {

  public static final String MFA_SECRET_LABEL = "start@fleenhistoria.com";
  public static final String MFA_SECRET_ISSUER = "Fleen Historia";
  public static final String AUTH_HEADER_KEY = "Authorization";
  public static final String AUTH_HEADER_PREFIX = "Bearer";
  public static final String TOKEN_TYPE_KEY = "tokenType";
  public static final String AUTHENTICATION_STATUS_KEY = "authenticationStatus";
  public static final long ACCESS_TOKEN_VALIDITY = getTimeInMillis(60, 60, 5, 0);
  public static final long REFRESH_TOKEN_VALIDITY = getTimeInMillis(60, 60, 24, 2);
  public static final long RESET_PASSWORD_TOKEN_VALIDITY = getTimeInMillis(60, 15, 0, 0);
  public static final String CLAIMS_USER_ID_KEY = "userId";
  public static final String CLAIMS_AUTHORITY_KEY = "authorities";
}
