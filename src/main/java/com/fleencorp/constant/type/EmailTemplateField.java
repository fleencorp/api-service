package com.fleencorp.constant.type;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

@Getter
public enum EmailTemplateField implements ApiParameter {

  CODE("code"),
  EMAIL_ADDRESS("emailAddress"),
  PHONE_NUMBER("phoneNumber"),
  FIRST_NAME("firstName"),
  LAST_NAME("lastName"),
  TITLE("title"),
  COMMENT("comment");

  private final String value;

  EmailTemplateField(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
