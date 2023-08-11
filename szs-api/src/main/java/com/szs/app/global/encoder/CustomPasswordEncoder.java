package com.szs.app.global.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

public interface CustomPasswordEncoder extends PasswordEncoder {

  String encode(CharSequence rawPassword);

  CharSequence decode(String encodedPassword);

  boolean matches(CharSequence rawPassword, String encodedPassword);

  default boolean upgradeEncoding(String encodedPassword) {
    return false;
  }

}
