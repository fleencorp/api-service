package com.fleencorp.base.constant.base;

public class ExceptionMessages {

  public static String unsupportedHttpRequestMethod() {
    return "HTTP Request Method Not Supported";
  }

  public static String unsupportedContentType() {
    return "Content Type Not Supported";
  }

  public static String invalidRequestBody() {
    return "Invalid Request Body";
  }

  public static String forbiddenAccess() {
    return "You are not allowed to access this resource";
  }

  public static String failedMailDelivery() {
    return "Mail delivery failed";
  }

  public static String invalidUser() {
    return "Invalid User";
  }

  public static String pathUrl() {
    return "path";
  }

  public static String missingHttpRequestParameters(String paramName, String paramType) {
    return String.format("Required parameter '%s' of type '%s' is missing", paramName, paramType);
  }

  public static String missingPathVariable(String paramName) {
    return String.format("Required parameter '%s' is missing", paramName);
  }

  public static String invalidArguments() {
    return "Invalid Arguments";
  }

  public static String nonValidRouteOrEndpoint() {
    return "The requested resource was not found on the server.";
  }

  public static String defaultMessage() {
    return "An error has occurred";
  }

  public static String unknownVariable() {
    return "Unknown";
  }
}

