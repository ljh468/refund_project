package com.szs.app.scrap.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CurrencyToLongDeserializer extends JsonDeserializer<Long> {

  @Override
  public Long deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    String value = parser.getText();
    try {
      return Double.valueOf(value.replaceAll(",", "")).longValue();
    } catch (NumberFormatException e) {
      throw new IOException("Failed to parse Long value: " + value, e);
    }
  }
}
