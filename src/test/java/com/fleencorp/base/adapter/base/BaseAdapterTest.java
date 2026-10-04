package com.fleencorp.base.adapter.base;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class BaseAdapterTest {

  private static final String BASE_URL = "https://api.example.com";
  private static final String KEY = "AIzaSy-secret-key-do-not-log";
  private static final String TOKEN = "refresh-token-do-not-log";

  private MockRestServiceServer server;
  private BaseAdapter adapter;
  private ListAppender<ILoggingEvent> logs;
  private Logger logger;

  @BeforeEach
  void setUp() {
    final RestTemplate restTemplate = new RestTemplate();
    server = MockRestServiceServer.bindTo(restTemplate).build();
    adapter = new BaseAdapter(BASE_URL, restTemplate, RestClient.create(restTemplate)) {};

    logger = (Logger) LoggerFactory.getLogger(BaseAdapter.class);
    logs = new ListAppender<>();
    logs.start();
    logger.addAppender(logs);
  }

  @AfterEach
  void tearDown() {
    logger.detachAppender(logs);
  }

  private void assertNothingSecretLogged() {
    assertFalse(logs.list.isEmpty(), "the call is still logged");
    for (final ILoggingEvent event : logs.list) {
      final String line = event.getFormattedMessage();
      assertFalse(line.contains(KEY), "key in log: " + line);
      assertFalse(line.contains(TOKEN), "token in log: " + line);
    }
  }

  @Test
  @DisplayName("doFormPost sends a form body, parses the answer and logs no field values")
  void doFormPostSendsFormAndLogsNoValues() {
    final URI uri = URI.create(BASE_URL + "/siteverify");
    final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("secret", KEY);
    form.add("response", TOKEN);
    server.expect(requestTo(uri))
      .andExpect(method(HttpMethod.POST))
      .andExpect(content().contentTypeCompatibleWith(APPLICATION_FORM_URLENCODED))
      .andExpect(content().formDataContains(Map.of("secret", KEY, "response", TOKEN)))
      .andRespond(withSuccess("{\"success\":true}", APPLICATION_JSON));

    final ResponseEntity<Map> response = adapter.doFormPost(uri, form, Map.class);

    server.verify();
    assertEquals(true, response.getBody().get("success"));
    assertNothingSecretLogged();
  }

  @Test
  @DisplayName("doFormPost returns a failed call as its status, logging no field values")
  void doFormPostFailure() {
    final URI uri = URI.create(BASE_URL + "/siteverify");
    final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("secret", KEY);
    server.expect(requestTo(uri)).andRespond(withServerError());

    final ResponseEntity<Map> response = adapter.doFormPost(uri, form, Map.class);

    assertTrue(response.getStatusCode().is5xxServerError());
    assertNothingSecretLogged();
  }
}
