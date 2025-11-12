package com.fleencorp.base.resolver.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleencorp.base.model.request.search.SearchRequest;
import com.fleencorp.base.resolver.SearchParam;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static java.net.URLDecoder.decode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.nonNull;

/**
 * Resolves method arguments annotated with {@code @SearchParam} by implementing Spring's HandlerMethodArgumentResolver interface.
 *
 * <p>This resolver is responsible for resolving method parameters annotated with {@code @SearchParam}
 * by determining if a parameter has the annotation, and if so, supporting its resolution.
 *
 * <p>The class is a Spring component and is designed to be automatically detected and registered in the Spring application context.
 */
public class SearchParamArgResolver implements HandlerMethodArgumentResolver {

  private static final Logger log = LoggerFactory.getLogger(SearchParamArgResolver.class);

  private final ObjectMapper mapper;

  /**
   * Constructs a new instance of SearchParamArgResolver.
   *
   * @param mapper The ObjectMapper used for converting query parameters to the desired method parameter type.
   */
  public SearchParamArgResolver(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  /**
   * Checks if the given method parameter is annotated with {@code @SearchParam}.
   *
   * @param parameter The method parameter to be checked.
   * @return {@code true} if the parameter is annotated with {@code @SearchParam}, {@code false} otherwise.
   */
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(SearchParam.class) != null;
  }

  /**
   * Resolves a method argument annotated with request parameters by parsing the request URL.
   *
   * @param parameter The method parameter to be resolved.
   * @param mavContainer The ModelAndViewContainer for the current request.
   * @param webRequest The NativeWebRequest representing the current request.
   * @param binderFactory The WebDataBinderFactory for creating data binders.
   * @return An object representing the resolved method argument.
   * @throws IllegalArgumentException If the resolved value cannot be cast to the expected type.
   */
  @Override
  public Object resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) throws IllegalArgumentException {
    // Retrieve the HttpServletRequest from the NativeWebRequest
    final HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

    // Build a UriComponentsBuilder from the full URL of the request
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(getFullURL(request));

    // Extract query parameters from the URL and convert them into a Map
    Map<String, String> queryMap = toQueryMap(builder.build().getQueryParams());

    try {
      // Convert the query parameter Map to the expected method parameter type using ObjectMapper
      var value = mapper.convertValue(queryMap, parameter.getParameterType());

      // Perform additional operations on the resolved value if necessary
      ((SearchRequest) value).toPageable();

      // Return the resolved value
      return value;
    } catch (ClassCastException ex) {
      // Log the error and throw an IllegalArgumentException if the value cannot be cast to the expected type
      log.error(ex.getMessage(), ex);
      throw new IllegalArgumentException(ex.getMessage());
    }
  }

  /**
   * Constructs the full URL (including query parameters) of the current request.
   *
   * @param request The HttpServletRequest object representing the current request.
   * @return A String representing the full URL of the current request, including query parameters if present.
   */
  private String getFullURL(HttpServletRequest request) {
    // Get the base URL of the current request
    StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());

    // Get the query string of the current request
    String queryString = request.getQueryString();

    // If there is no query string, return the base URL
    if (queryString == null) {
      return requestURL.toString();
    }
    // If there is a query string, append it to the base URL and return the full URL
    else {
      return requestURL.append('?').append(queryString).toString();
    }
  }


  /**
   * Converts a MultiValueMap of query parameters into a regular Map of decoded query parameters.
   *
   * @param queryParams The MultiValueMap containing query parameters.
   * @return A Map containing decoded query parameters, where each key-value pair represents a parameter name and its decoded value.
   */
  public Map<String, String> toQueryMap(MultiValueMap<String, String> queryParams) {
    // Initialize a new HashMap to store decoded query parameters
    Map<String, String> queryMap = new HashMap<>();

    // Iterate through each key in the MultiValueMap
    for (String key : queryParams.keySet()) {
      // Retrieve the first value associated with the key
      String decodedValue = queryParams.getFirst(key);

      // If the value is not null, decode it using the UTF-8 charset
      if (nonNull(decodedValue)) {
        decodedValue = decode(decodedValue, UTF_8);
      }

      // Add the decoded value to the queryMap with the corresponding key
      queryMap.put(key, decodedValue);
    }

    // Return the Map containing decoded query parameters
    return queryMap;
  }

}
