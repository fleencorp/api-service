package com.fleencorp.constant.type.general.externalsystem.google.oauth;

import com.fleencorp.fleenhistoria.api.ApiParameter;
import lombok.Getter;

/**
 * Enumeration representing types of data stores used in Google OAuth.
 * These data stores are used to store OAuth2 tokens and related information.
 * Each type corresponds to a specific storage mechanism for OAuth2 tokens.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Getter
public enum DataStoreType implements ApiParameter {

  /**
   * File-based data store.
   */
  FILE("File"),

  /**
   * In-memory data store.
   */
  MEMORY("Memory"),

  /**
   * Database-backed data store.
   */
  DB("DB");

  /**
   * The value representing the data store type.
   */
  private final String value;

  /**
   * Constructs a new data store type with the specified value.
   *
   * @param value The value representing the data store type.
   */
  DataStoreType(String value) {
    this.value = value;
  }
}
