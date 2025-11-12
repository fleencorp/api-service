package com.fleencorp.base.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleencorp.base.converter.impl.security.StringCryptoConverter;
import com.fleencorp.base.resolver.impl.SearchParamArgResolver;
import com.fleencorp.base.service.BasicCountryService;
import com.fleencorp.base.service.EmailService;
import com.fleencorp.base.service.PhoneService;
import com.fleencorp.base.util.JsonUtil;
import com.fleencorp.base.util.security.EncryptionUtils;
import com.fleencorp.base.validator.impl.CountryExistValidator;
import com.fleencorp.base.validator.impl.EmailAddressAlreadyExistValidator;
import com.fleencorp.base.validator.impl.PhoneNumberAlreadyExistValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class FleenBaseConfiguration {

  @Bean
  public JsonUtil jsonUtil(final ObjectMapper objectMapper) {
    return new JsonUtil(objectMapper);
  }

  @Bean
  public CountryExistValidator countryExistValidator(final BasicCountryService basicCountryService) {
    return new CountryExistValidator(basicCountryService);
  }

  @Bean
  public EmailAddressAlreadyExistValidator emailAddressAlreadyExistValidator(final EmailService emailService) {
    return new EmailAddressAlreadyExistValidator(emailService);
  }

  @Bean
  public PhoneNumberAlreadyExistValidator phoneNumberAlreadyExistValidator(final PhoneService phoneService) {
    return new PhoneNumberAlreadyExistValidator(phoneService);
  }

  @Bean
  public SearchParamArgResolver searchParamArgResolver(final ObjectMapper objectMapper) {
    return new SearchParamArgResolver(objectMapper);
  }

  @Bean
  public EncryptionUtils encryptionUtils(@Value("${entity.field.encryption.key}") final String encryptionKey) {
    return new EncryptionUtils(encryptionKey);
  }

  @Bean
  public StringCryptoConverter stringCryptoConverter(final EncryptionUtils encryptionUtils) {
    return new StringCryptoConverter(encryptionUtils);
  }
}
