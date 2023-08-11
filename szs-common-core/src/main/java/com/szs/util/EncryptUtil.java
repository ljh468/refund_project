package com.szs.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

public class EncryptUtil {

  // 초기 백터(16바이트 크기를 가지며, 16바이트 단위로 암호화시, 암호화할 총 길이가 16바이트가 되지 못하면 뒤에 추가하는 바이트)
  final static byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

  final static String key = "szs_spring_boot3"; // 16글자(영문자 1글자당 1바이트임)

  // AES128 CBC 암호화
  public static String encAES128CBC(String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
      InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

    byte[] textBytes = str.getBytes(StandardCharsets.UTF_8);
    AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
    SecretKeySpec newKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    Cipher cipher = null;
    cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
    return Base64.getEncoder().encodeToString(cipher.doFinal(textBytes));
  }

  // AES128 CBC 복호화
  public static String decAES128CBC(String str) throws NoSuchAlgorithmException, NoSuchPaddingException,
      InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {

    byte[] textBytes = Base64.getDecoder().decode(str);
    AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
    SecretKeySpec newKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
    return new String(cipher.doFinal(textBytes), StandardCharsets.UTF_8);
  }
}
