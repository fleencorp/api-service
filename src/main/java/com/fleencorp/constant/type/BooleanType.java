package com.fleencorp.constant.type;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum BooleanType implements ApiParameter {

  TRUE("true"),
  FALSE("false");

  private final String value;

  BooleanType(String value) {
    this.value = value;
  }

}