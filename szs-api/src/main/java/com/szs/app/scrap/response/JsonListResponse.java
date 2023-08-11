package com.szs.app.scrap.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.szs.app.scrap.serializer.CurrencyToLongDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonListResponse {

  @JsonProperty("급여")
  private List<IncomeSalaryResponse> incomeSalaries;

  @JsonProperty("산출세액")
  @JsonDeserialize(using = CurrencyToLongDeserializer.class)
  private Long calculatedTax;

  @JsonProperty("소득공제")
  private List<DeductionResponse> deductions;

}
