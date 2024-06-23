package com.fleencorp.util.security;

import com.fleencorp.fleenhistoria.exception.security.EncryptionUtilsException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class EncryptionUtils {

  public EncryptionUtils(@Value("${com.fleenhistoria.field.encryption.key}") String encryptionKey) {
    this.encryptionKey = encryptionKey;
  }

  private final String encryptionKey;
  private static final String TRANSFORMATION = "AES/GCM/NoPadding";
  private static final String ALGORITHM = "AES";

  public String encrypt(String value) {
    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.ENCRYPT_MODE,
          new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8),
              ALGORITHM),
          new GCMParameterSpec(128,encryptionKey.getBytes(StandardCharsets.UTF_8)));
      return Base64.encodeBase64String(cipher.doFinal(value.getBytes()));
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      throw new EncryptionUtilsException(
          String.format("%s:%s", ex.getClass().getName(),
              ex.getMessage()));
    }
  }

  public String decrypt(String encrypted) {
    try {
      Cipher cipher = Cipher.getInstance(TRANSFORMATION);
      cipher.init(Cipher.DECRYPT_MODE,
          new SecretKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8), ALGORITHM),
          new GCMParameterSpec(128,encryptionKey.getBytes(StandardCharsets.UTF_8)));
      return new String(cipher.doFinal(Base64.decodeBase64(encrypted)));
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
      throw new EncryptionUtilsException(
          String.format("%s:%s", ex.getClass().getName(),
              ex.getMessage()));
    }
  }
}
