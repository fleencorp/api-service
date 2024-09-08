package com.fleencorp.base.model.response.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fleencorp.base.constant.base.ExceptionMessages;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static com.fleencorp.base.util.datetime.DateFormatUtil.DATE_TIME;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "message",
  "status",
  "timestamp"
})
public class ErrorResponse {

  @JsonProperty("message")
  private String message;

  @JsonProperty("reason")
  private String reason;

  @JsonProperty("error_type")
  private ValidationTypeError errorType;

  @JsonProperty("status")
  private Object status;

  @JsonFormat(shape = STRING, pattern = DATE_TIME)
  @JsonProperty("timestamp")
  private LocalDateTime timestamp;

  @JsonProperty("fields")
  private List<Map<String, Object>> fieldErrors = new ArrayList<>();

  /**
   * Creates an {@link ErrorResponse} with the specified message and HTTP status.
   *
   * <p>This static method constructs an {@link ErrorResponse} using the provided message and HTTP status,
   * setting the status code, reason phrase, and the current timestamp.</p>
   *
   * @param message the error message to be included in the response.
   * @param status  the HTTP status to be included in the response.
   * @return a new {@link ErrorResponse} instance with the specified message, status, reason, and timestamp.
   */
  public static ErrorResponse of(String message, HttpStatus status) {
    return ErrorResponse.builder()
      .message(message)
      .status(status.value())
      .reason(status.getReasonPhrase())
      .timestamp(LocalDateTime.now())
      .build();
  }

  /**
   * Creates an {@link ErrorResponse} with the specified message, HTTP status, and field-specific error details.
   *
   * <p>This static method constructs an {@link ErrorResponse} using the provided message, HTTP status, and
   * a list of field-specific error details. The response includes the status code, reason phrase, current timestamp,
   * and sets the field errors and default error type.</p>
   *
   * @param message       the error message to be included in the response.
   * @param status        the HTTP status to be included in the response.
   * @param fieldErrors   a list of field-specific error details to be included in the response.
   * @return a new {@link ErrorResponse} instance with the specified message, status, reason, timestamp, field errors,
   *         and default error type.
   */

  public static ErrorResponse of(String message, HttpStatus status, List<Map<String, Object>> fieldErrors) {
    ErrorResponse response = ErrorResponse.builder()
      .message(message)
      .status(status.value())
      .reason(status.getReasonPhrase())
      .timestamp(LocalDateTime.now())
      .build();
    response.setFieldErrors(fieldErrors);
    response.setDefaultErrorType();
    return response;
  }

  /**
   * Creates a default {@link ErrorResponse} with a default message.
   *
   * <p>This static method constructs an {@link ErrorResponse} with a default error message and the current timestamp.</p>
   *
   * @return a new {@link ErrorResponse} instance with a default message and timestamp.
   */
  public static ErrorResponse of() {
    return ErrorResponse.builder()
      .message(ExceptionMessages.defaultMessage())
      .timestamp(LocalDateTime.now())
      .build();
  }

  public void setDefaultErrorType() {
    errorType = ValidationTypeError.dataValidation();
  }

  /**
   * Enum representing different types of validation errors.
   *
   * <p>This enum is used to categorize validation errors. Currently, it includes only one type: {@code DATA_VALIDATION}.</p>
   *
   * @author Yusuf Alamu Musa
   * @version 1.0
   */
  public enum ValidationTypeError {
    DATA_VALIDATION;

    /**
     * Retrieves the {@code DATA_VALIDATION} constant of this enum.
     *
     * <p>This static method provides a convenient way to obtain the {@code DATA_VALIDATION} value of the enum.</p>
     *
     * @return the {@code DATA_VALIDATION} constant.
     */
    public static ValidationTypeError dataValidation() {
      return ValidationTypeError.DATA_VALIDATION;
    }
  }
}
