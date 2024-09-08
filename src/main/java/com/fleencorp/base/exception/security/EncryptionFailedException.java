package com.fleencorp.base.exception.security;

import com.fleencorp.base.exception.FleenException;

public class EncryptionFailedException extends FleenException {

  @Override
  public String getMessageCode() {
    return "encryption.failed";
  }

  public EncryptionFailedException() {
    super();
  }
}
