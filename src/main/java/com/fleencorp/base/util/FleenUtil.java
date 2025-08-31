package com.fleencorp.base.util;

import com.fleencorp.base.constant.type.BooleanType;
import com.fleencorp.base.model.view.search.SearchResult;
import com.fleencorp.localizer.model.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.FileCopyUtils;

import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.data.domain.Sort.Direction.ASC;

public class FleenUtil {

  private static final Logger log = LoggerFactory.getLogger(FleenUtil.class);

  public static String[] getNullPropertyNames(Object source) {
    if (source != null) {
      final BeanWrapper wrappedSource = new BeanWrapperImpl(source);

      return Stream.of(wrappedSource.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> {
          try {
            return Objects.isNull(wrappedSource.getPropertyValue(propertyName));
          } catch (Exception e) {
            return false;
          }
        })
        .toArray(String[]::new);
    }
    return new String[] {};
  }

  public static String readResourceFile(String path) {
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource(path);
    return asString(resource);
  }

  /**
   * Converts the content of the provided {@link Resource} into a {@link String}.
   *
   * <p>This method reads the content from the given resource using UTF-8 encoding and converts it into a string.
   * It handles input streams by wrapping them in a {@link Reader} and copying the content using {@link FileCopyUtils}.</p>
   *
   * @param resource the resource from which content is to be read
   * @return the content of the resource as a {@link String}
   * @throws UncheckedIOException if an {@link IOException} occurs during reading
   */
  private static String asString(final Resource resource) {
    try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Validates whether the given string represents a valid number.
   *
   * <p>This method checks if the provided string is non-null and matches the regular expression for a valid number.
   * The regular expression accommodates optional negative signs, integers, and decimal numbers.</p>
   *
   * <p>Valid formats include:</p>
   * <ul>
   *   <li>Integer numbers (e.g., "123", "-456")</li>
   *   <li>Decimal numbers (e.g., "123.45", "-456.78")</li>
   * </ul>
   *
   * @param input the string to be validated
   * @return {@code true} if the input is non-null and matches the number format; {@code false} otherwise
   */
  public static boolean isValidNumber(final String input) {
    return nonNull(input) && input.matches("-?\\d+(\\.\\d+)?");
  }

  /**
   * Creates a Pageable object for pagination and sorting in Spring Data.
   * This method generates a Pageable object for a specific page number, page size, sorting field, and direction.
   *
   * @param pageNo    An integer representing the page number.
   * @param pageSize  An integer representing the number of items per page.
   * @param sortBy    A string representing the field to sort by.
   * @param sortDir   A string representing the sort direction ("asc" for ascending, "desc" for descending).
   * @return A Pageable object configured for pagination and sorting.
   */
  public static Pageable createPageable(int pageNo, int pageSize, String sortBy, String sortDir) {
    // Determine the sort direction based on the provided sort direction string
    Sort sort = sortDir.equalsIgnoreCase(ASC.name())
      ? Sort.by(sortBy).ascending()
      : Sort.by(sortBy).descending();
    return PageRequest.of(pageNo, pageSize, sort);
  }

  /**
   * A utility method to check if a varargs list of objects is not empty or null.
   * It examines an individual element in the array of objects and determines if they are all non-empty or non-null.
   *
   * @param args A varargs parameter representing an array of objects to be checked for emptiness or nullity.
   * @return A boolean value: true if all provided objects are non-empty or non-null, false otherwise.
   */
  public static boolean areNotEmpty(Object...args) {
    if (isEmpty(args)) {
      return false;
    } else {
      long arrLength = args.length;
      long totalNonEmptyElement = Arrays
        .stream(args)
        .filter(arg -> arg instanceof String
          ? isNotBlank((String) arg)
          : nonNull(arg))
        .count();
      return arrLength == totalNonEmptyElement;
    }
  }

/*  *//**
   * Converts a list of values and a Page object into a standardized SearchResultView object.
   * This method constructs a SearchResultView object, incorporating pagination and content details from a Page object.
   *
   * @param values A list of values retrieved from a database query or API for example REST.
   * <br/>
   * @param page   A Page object containing pagination details (can be null for non-paginated results).
   * @return A SearchResultView object containing paginated or non-paginated search results.
   *//*
  public static SearchResultView toSearchResult(List<?> values, CustomPage page) {
    if (page != null) {
      return SearchResultView.builder()
        .isFirst(page.isFirst())
        .isLast(page.isLast())
        .totalPages(page.getTotalPages())
        .totalEntries(page.getTotalElements())
        .pageNo(page.getNumber())
        .pageSize(page.getSize())
        .nextPageToken(page.getNextPageToken())
        .prevPageToken(page.getPrevPageToken())
        .values(values)
        .build();
    }
    return SearchResultView.builder()
      .values(values)
      .build();
  }*/

  public static <V, T> SearchResult<V> toSearchResult(Collection<V> values, Page<T> page) {
    if (page != null) {
      SearchResult<V> searchResult = new SearchResult<>();
      searchResult.setFirst(page.isFirst());
      searchResult.setLast(page.isLast());
      searchResult.setTotalPages(page.getTotalPages());
      searchResult.setTotalEntries(page.getTotalElements());
      searchResult.setPageNo(page.getNumber());
      searchResult.setPageSize(page.getSize());
      searchResult.setValues(values);
      return searchResult;
    }

    SearchResult<V> searchResult = new SearchResult<>();
    searchResult.setValues(values);
    return searchResult;
  }

  /**
   * Checks if a provided string represents a boolean value.
   * This method evaluates whether the given string matches the defined TRUE or FALSE boolean values.
   *
   * @param value A string representing a potential boolean value.
   * @return A boolean indicating if the provided string represents a boolean value (TRUE or FALSE).
   */
  public static boolean isBoolean(String value) {
    return BooleanType.TRUE.getValue().equalsIgnoreCase(value) || BooleanType.FALSE.getValue().equalsIgnoreCase(value);
  }

  /**
   * Sets a value using a setter if the value provided by the supplier is non-null.
   *
   * This generic helper method takes a supplier that provides a value and a setter that consumes the value.
   * If the value provided by the supplier is non-null, it is passed to the setter.
   *
   * @param <T> The type of the value.
   * @param supplier A {@link Supplier} that provides the value.
   * @param setter A {@link Consumer} that consumes the value.
   */
  public static <T> void setIfNonNull(Supplier<T> supplier, Consumer<T> setter) {
    // Get the value from the supplier
    T value = supplier.get();
    // If the value is non-null, pass it to the setter
    if (value != null) {
      setter.accept(value);
    }
  }

  /**
   * Handles search results based on the total elements in the provided Page object.
   * If the page contains elements, the {@code nonEmptySupplier} is executed to return the result.
   * If the page is empty, the {@code emptySupplier} is executed instead.
   *
   * @param <T> The type of the result returned by the suppliers.
   * @param page The Page object containing the search results to be evaluated.
   * @param nonEmptySupplier The supplier to be invoked when the page contains elements.
   * @param emptySupplier The supplier to be invoked when the page is empty.
   * @return The result of the {@code nonEmptySupplier} if the page has elements,
   *         or the result of the {@code emptySupplier} if the page is empty.
   * @throws NullPointerException if any of the suppliers are null.
   */
  public static <T extends ApiResponse> T handleSearchResult(Page<?> page, Supplier<T> nonEmptySupplier, Supplier<T> emptySupplier) {
    if (nonNull(page) && page.getTotalElements() > 0) {
      return nonEmptySupplier.get();
    } else {
      return emptySupplier.get();
    }
  }

}
