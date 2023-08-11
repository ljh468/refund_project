package com.szs.app.global.encoder;

import static com.szs.util.EncryptUtil.decAES128CBC;
import static com.szs.util.EncryptUtil.encAES128CBC;

public class CustomAesPasswordEncoder implements CustomPasswordEncoder {

  @Override
  public String encode(CharSequence rawPassword) {
    try {
      return encAES128CBC(rawPassword.toString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public String decode(String encodedPassword) {
    try {
      return decAES128CBC(encodedPassword);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    try {
      String decrypted = decAES128CBC(encodedPassword);
      return decrypted.equals(rawPassword.toString());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
