package com.fleencorp.base.util.security;

import jakarta.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The AuthUtils contains implementations and methods for dealing with application and user related security
 */
public class AuthUtil {

  private static final Logger log = LoggerFactory.getLogger(AuthUtil.class);

  public static final String BEARER = "Bearer ";
  public static final String BASIC = "Basic ";

  private AuthUtil() {
  }

  public static String getBearerToken(@NotEmpty String token) {
    return BEARER + token;
  }

  /**
   * This method takes in a token string to check and verify whether it is valid
   *
   * @param token the bearer token
   * @return the actual token without the "Bearer" prefix
   * @throws RuntimeException if the token is not a bearer token
   */
  public static String stripBearerToken(@NotEmpty String token) {
    if (!token.startsWith(BEARER)) {
      String message = "Token is not a bearer token!";
      log.error(message);
      throw new RuntimeException(message);
    }
    return token.substring(BEARER.length());
  }

  /**
   * Checks if a string is a bearer token or begins with "Bearer" prefix
   *
   * @param token the bearer token
   * @return true or false if the string begins with the "Bearer" prefix
   */
  public static boolean isBearerToken(String token) {
    return token != null && token.startsWith(BEARER);
  }

}
