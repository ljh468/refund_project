package com.szs.app.domain.response;

import com.szs.app.domain.entity.Deduction;
import lombok.*;


import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeductionResponse {

  private Long id;

  private String paymentType;

  private Double paymentAmount;

  public static DeductionResponse from(Deduction deduction) {
    if (isNull(deduction)) {
      return null;
    }
    return DeductionResponse.builder()
                            .id(deduction.getId())
                            .paymentType(deduction.getPaymentType().getName())
                            .paymentAmount(deduction.getPaymentAmount())
                            .build();
  }
}
