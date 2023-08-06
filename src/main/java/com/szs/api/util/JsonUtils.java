package com.szs.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JsonUtils {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static <T> List<T> fromJsonList(InputStream inputStream, Class<T> tClass) {
    try {
      JavaType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, tClass);
      return objectMapper.readValue(inputStream, javaType);
    } catch (Exception e) {
      throw new RuntimeException("Failed to parse JSON", e);
    }
  }

  public static String mapToJson(Map<String, Object> map) {
    try {
      return objectMapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to convert map to JSON", e);
    }
  }
}
