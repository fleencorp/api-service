package com.fleencorp.base.constant.type;

import com.fleencorp.base.constant.base.ApiParameter;

public enum BooleanType implements ApiParameter {

  TRUE("true"),
  FALSE("false");

  private final String value;

  BooleanType(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return value;
  }
}