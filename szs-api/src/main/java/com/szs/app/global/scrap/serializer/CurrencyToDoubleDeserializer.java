package com.szs.app.global.scrap.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class CurrencyToDoubleDeserializer extends JsonDeserializer<Double> {

  @Override
  public Double deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    String value = parser.getText();
    try {
      return Double.valueOf(value.replaceAll(",", ""));
    } catch (NumberFormatException e) {
      throw new IOException("Failed to parse Double value: " + value, e);
    }
  }
}
