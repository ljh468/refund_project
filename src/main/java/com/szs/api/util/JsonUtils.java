package com.szs.api.util;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class JsonUtils {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static <T> List<T> fromJsonList(InputStream inputStream, Class<T> tClass) {
    try {
      JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, tClass);
      return mapper.readValue(inputStream, javaType);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse JSON", e);
    }
  }
}
