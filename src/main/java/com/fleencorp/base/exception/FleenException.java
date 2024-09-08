package com.fleencorp.base.exception;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FleenException extends RuntimeException {

  public String getMessageCode() {
    return "";
  }
  protected String message = "";
  protected Object[] params = new Object[] {};

  public FleenException(Object...params) {
    super();
    this.params = params;
  }

  public FleenException() {
    super();
  }
}
