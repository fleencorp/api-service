package com.fleencorp.base.exception.security;

import com.fleencorp.base.exception.FleenException;

public class DecryptionFailedException extends FleenException {

  @Override
  public String getMessageCode() {
    return "decryption.failed";
  }

  public DecryptionFailedException() {
    super();
  }
}
