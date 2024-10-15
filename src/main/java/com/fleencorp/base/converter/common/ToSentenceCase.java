package com.fleencorp.base.converter.common;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fleencorp.base.converter.impl.common.ToSentenceCaseConverter;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(converter = ToSentenceCaseConverter.class)
@JsonDeserialize(converter = ToSentenceCaseConverter.class)
public @interface ToSentenceCase {
}

