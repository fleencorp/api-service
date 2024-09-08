package com.fleencorp.base.adapter.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleencorp.base.constant.base.ApiParameter;
import com.fleencorp.base.constant.base.EndpointBlock;
import com.fleencorp.base.util.security.AuthUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

/**
 * Base adapter class providing common functionality for making HTTP requests to APIs.
 * This class encapsulates common functionality such as initializing REST clients,
 * building URIs, setting headers, and making HTTP calls.
 *
 * @author Yusuf Alamu Musa
 * @version 1.0
 */
@Slf4j
@Setter
public class BaseAdapter {

  /**
   * The base URL for the API endpoints.
   */
  @NotBlank
  protected String baseUrl;

  /**
   * The RestTemplate instance used for making HTTP requests.
   */
  protected RestTemplate restTemplate;

  /**
   * The RestClient instance used for making HTTP requests.
   */
  protected final RestClient restClient;

  /**
   * Constructs a new {@code BaseAdapter} with the specified base URL, {@link RestTemplate}, and {@link RestClient}.
   *
   * <p>This protected constructor initializes the {@code BaseAdapter} with the given base URL,
   * a {@link RestTemplate} for HTTP requests, and a custom {@link RestClient} for additional client configurations
   * or features. It sets up the adapter with the necessary components for making RESTful calls to external services.</p>
   *
   * @param baseUrl      the base URL for the REST API endpoints that this adapter will interact with.
   * @param restTemplate the {@link RestTemplate} used for making HTTP requests.
   * @param restClient   the {@link RestClient} used for managing HTTP client configurations and requests.
   */
  protected BaseAdapter(
      final String baseUrl,
      final RestTemplate restTemplate,
      final RestClient restClient) {
    this.baseUrl = baseUrl;
    this.restTemplate = restTemplate;
    this.restClient = restClient;
  }

  /**
   * Makes an HTTP call to the specified URI with the given method, headers, body, and response model type.
   *
   * @param uri            The URI to make the HTTP call to
   * @param method         The HTTP method to use for the call
   * @param headers        The headers to include in the request
   * @param body           The body of the request
   * @param responseModel  The type of the response model
   * @return ResponseEntity containing the response data
   */
  public <T> ResponseEntity<T> doCall(@NonNull final URI uri, @NonNull final HttpMethod method,
                                      @Nullable final Map<String, String> headers, @Nullable final Object body, @NonNull final Class<T> responseModel) {
    // Log the HTTP call details
    log.info(String.format("HTTP call to url=%s with method=%s and body=%s", uri, method.name(),
      getPayloadBodyAsString(body)));

    // Set request headers
    final HttpHeaders requestHeaders = getHeaders();
    if (headers != null) {
      headers.forEach(requestHeaders::add);
    }

    try {
      // Make the HTTP call using the RestClient
      return restClient
        .method(method)
        .uri(uri)
        .headers(newHeaders -> newHeaders.addAll(requestHeaders))
        .body(getPayloadBodyAsString(body))
        .retrieve()
        .toEntity(responseModel);
    } catch (final HttpStatusCodeException e) {
      // Handle HTTP status code errors
      log.error(String.format(
        "An error occurred while HTTP call to url=%s with method=%s and body=%s: %s", uri,
        method.name(),
        getPayloadBodyAsString(body), e.getMessage()));
      final HttpHeaders errorHeaders = e.getResponseHeaders();
      final String errorBody = e.getResponseBodyAsString();

      return ResponseEntity.status(e.getStatusCode())
        .headers(errorHeaders).body((T) errorBody);
    }
  }

  /**
   * Executes an HTTP request to the specified URI with the given method, headers, and body,
   * and returns the response as a {@link ResponseEntity} of the specified type.
   *
   * <p>This method performs an HTTP call using the configured {@link RestClient}, logging the request details
   * including the URI, HTTP method, and request body. It constructs the request headers by adding any provided
   * headers to the default headers. The response is returned as a {@link ResponseEntity} of the expected
   * response type, specified by the {@link ParameterizedTypeReference}. In case of an HTTP error,
   * it catches the {@link HttpStatusCodeException} and logs the error details, then returns an
   * appropriate {@link ResponseEntity} containing the error status, headers, and body.</p>
   *
   * @param <T>           the type of the response body.
   * @param uri           the {@link URI} to which the HTTP request is sent. Must not be {@code null}.
   * @param method        the {@link HttpMethod} to use for the request. Must not be {@code null}.
   * @param headers       a map of additional headers to include in the request, may be {@code null}.
   * @param body          the request body to be sent, may be {@code null}. If {@code null}, an empty body is used.
   * @param responseModel a {@link ParameterizedTypeReference} indicating the type of the response body. Must not be {@code null}.
   * @return a {@link ResponseEntity} containing the response status, headers, and body of type {@code T}.
   * @throws HttpStatusCodeException if the HTTP call results in an error response status.
   */
  public <T> ResponseEntity<T> doCall(@NonNull final URI uri, @NonNull final HttpMethod method,
                                      @Nullable final Map<String, String> headers, @Nullable final Object body,
                                      @NonNull final ParameterizedTypeReference<T> responseModel) {

    log.info(String.format("HTTP call to url=%s with method=%s and body=%s", uri, method.name(),
      getPayloadBodyAsString(body)));

    final HttpHeaders requestHeaders = getHeaders();
    if (headers != null) {
      headers.forEach(requestHeaders::add);
    }

    try {
      return restClient
        .method(method)
        .uri(uri)
        .headers(newHeaders -> newHeaders.addAll(requestHeaders))
        .body(getPayloadBodyAsString(body))
        .retrieve()
        .toEntity(responseModel);
    } catch (final HttpStatusCodeException e) {
      log.error(String.format(
        "An error occurred while HTTP call to url=%s with method=%s and body=%s: %s", uri,
        method.name(),
        getPayloadBodyAsString(body), e.getMessage()));
      final HttpHeaders errorHeaders = e.getResponseHeaders();
      final String errorBody = e.getResponseBodyAsString();

      return ResponseEntity.status(e.getStatusCode())
        .headers(errorHeaders)
        .body((T) errorBody);
    }
  }

  /**
   * Converts the payload body object to a string representation.
   *
   * @param body The payload body object
   * @return The string representation of the payload body
   */
  public static String getPayloadBodyAsString(final Object body) {
    String payloadAsString = "";
    if (body instanceof String) {
      payloadAsString = (String) body;
    } else {
      try {
        payloadAsString = new ObjectMapper().writeValueAsString(body);
      } catch (final JsonProcessingException ignored) {
      }
    }
    return payloadAsString;
  }

  /**
   * Initializes a UriComponentsBuilder with the base URL and additional endpoint blocks.
   *
   * @param urlBlocks The additional endpoint blocks
   * @return The initialized UriComponentsBuilder
   */
  protected UriComponentsBuilder initUriBuilder(final EndpointBlock... urlBlocks) {
    final StringBuilder urlBuilder = new StringBuilder(baseUrl);
    for (final EndpointBlock block : urlBlocks) {
      if (block != null) {
        urlBuilder.append(block.value());
      }
    }
    return UriComponentsBuilder.fromHttpUrl(urlBuilder.toString());
  }

  /**
   * Retrieves the headers to be included in the HTTP request.
   *
   * @return The HttpHeaders containing the request headers
   */
  protected HttpHeaders getHeaders() {
    final HttpHeaders requestHeaders = new HttpHeaders();
    requestHeaders.setContentType(APPLICATION_JSON);
    return requestHeaders;
  }

  /**
   * Constructs an HTTP header with the given authorization value.
   *
   * @param value The authorization value to be included in the header.
   * @return A {@link Map} containing the HTTP header with the authorization value.
   */
  protected Map<String, String> getAuthHeader(final String value) {
    final Map<String, String> headers = new HashMap<>();
    headers.put(HttpHeaders.AUTHORIZATION, value);
    return headers;
  }

  /**
   * Constructs an HTTP header with a Bearer token authorization.
   *
   * @param token The Bearer token to be included in the authorization header.
   * @return A {@link Map} containing the HTTP header with the Bearer token authorization.
   */
  protected Map<String, String> getAuthHeaderWithBearerToken(final String token) {
    return getAuthHeader(AuthUtil.getBearerToken(token));
  }

  /**
   * Builds an endpoint path variable based on the provided object.
   *
   * @param object The object to append to the base endpoint path.
   * @return An {@link EndpointBlock} representing the constructed endpoint block.
   */
  protected EndpointBlock buildPathVar(final Object object) {
    return new BaseEndpointBlock("/" + object);
  }

  /**
   * Builds a URI from the base URL and additional endpoint blocks.
   *
   * @param urlBlocks The additional endpoint blocks
   * @return The built URI
   */
  protected URI buildUri(final EndpointBlock... urlBlocks) {
    return initUriBuilder(urlBlocks).build().toUri();
  }

  /**
   * Builds a {@link URI} by constructing the base URI with specified endpoint blocks and appending query parameters.
   *
   * <p>This protected method initializes a URI builder with the given URL blocks, representing parts of the
   * endpoint path. It then iterates over the provided query parameters, adding each as a query parameter to
   * the URI. The resulting {@link URI} is built from the constructed path and query parameters.</p>
   *
   * @param queryParams a map of query parameters where the key is an {@link ApiParameter} and the value is the
   *                    parameter's value to be included in the URI.
   * @param urlBlocks   an array of {@link EndpointBlock} elements that define parts of the endpoint path.
   * @return the constructed {@link URI} combining the base path and the specified query parameters.
   */
  protected URI buildUri(final Map<ApiParameter, String> queryParams, final EndpointBlock... urlBlocks) {
    final UriComponentsBuilder uriComponentsBuilder = initUriBuilder(urlBlocks);
    queryParams.forEach((k, v) -> uriComponentsBuilder.queryParam(k.getValue(), v));
    return uriComponentsBuilder.build().toUri();
  }
}
