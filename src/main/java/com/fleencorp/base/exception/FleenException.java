package com.fleencorp.base.exception;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class FleenException extends RuntimeException {

  public String getMessageCode() {
    return "";
  }

  protected String message = "";
  protected Object[] params = new Object[] {};

  public Map<String, Object> getDetails() {
    return new HashMap<>();
  }

  public FleenException(Object...params) {
    super();
    this.params = params;
  }

  public FleenException() {
    super();
  }
}
