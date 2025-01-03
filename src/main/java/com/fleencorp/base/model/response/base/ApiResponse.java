package com.fleencorp.base.model.response.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ApiResponse {

  @JsonIgnore
  abstract public String getMessageCode();

  @JsonIgnore
  public Object[] getParams() {
    return new Object[] {};
  }

  @JsonProperty("message")
  protected String message;
}
