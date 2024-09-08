package com.fleencorp.base.converter.common;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fleencorp.base.converter.impl.common.ToLowerCaseConverter;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(converter = ToLowerCaseConverter.class)
@JsonDeserialize(converter = ToLowerCaseConverter.class)
public @interface ToLowerCase {
}
