package com.szs.app.global.scrap.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.szs.app.global.scrap.serializer.CurrencyToDoubleDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeductionResponse {

  @JsonProperty("소득구분")
  private String paymentType;

  @JsonAlias({"금액", "총납임금액"})
  @JsonDeserialize(using = CurrencyToDoubleDeserializer.class)
  private Double paymentAmount;

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("paymentType", paymentType)
        .append("paymentAmount", paymentAmount)
        .toString();
  }
}

