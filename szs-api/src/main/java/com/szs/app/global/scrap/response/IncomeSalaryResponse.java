package com.szs.app.global.scrap.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.szs.app.global.scrap.serializer.CurrencyToLongDeserializer;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomeSalaryResponse {

  @JsonProperty("소득내역")
  private String incomeDetail;

  @JsonProperty("소득구분")
  private String incomeType;

  @JsonProperty("기업명")
  private String companyName;

  @JsonProperty("사업자등록번호")
  private String companyRegNo;

  @JsonProperty("총지급액")
  @JsonDeserialize(using = CurrencyToLongDeserializer.class)
  private Long paymentTotal;

  @JsonProperty("지급일")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate paymentDate;

  @JsonProperty("업무시작일")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate workStartDate;

  @JsonProperty("업무종료일")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDate workEndDate;

}
