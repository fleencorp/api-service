package com.fleencorp.base.exception.externalsystem;

import static java.lang.String.format;

public class ExternalSystemException extends RuntimeException {

  public static final String MESSAGE = "Error occurred while communicating with external system: %s";

  public ExternalSystemException(String externalSystemType) {
    super(format(MESSAGE, externalSystemType));
  }
}
