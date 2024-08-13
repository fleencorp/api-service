package com.fleencorp.base.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Custom serializer for {@link Enum} types that serializes enum values
 * as their string representation.
 *
 * <p>This serializer writes the string representation of an enum value
 * using the {@link Enum#toString()} method. It is useful for serializing
 * enums in JSON format where the enum's name should be represented as a string.</p>
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 *
 * @see JsonSerializer
 * @see Enum#toString()
 */
public class ToStringEnumSerializer extends JsonSerializer<Enum<?>> {

  /**
   * Serializes the given enum value as a string.
   *
   * <p>This method uses the {@link Enum#toString()} method to obtain the
   * string representation of the enum value and writes it to the JSON output.</p>
   *
   * @param value the enum value to serialize. This must not be {@code null}.
   * @param gen the {@link JsonGenerator} used to write the JSON output.
   * @param serializers the {@link SerializerProvider} that can be used to get serializers
   *                    for other types.
   * @throws IOException if an I/O error occurs while writing the JSON output.
   */
  @Override
  public void serialize(Enum<?> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeString(value.toString());
  }
}
