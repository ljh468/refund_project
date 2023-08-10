package com.szs.app.domain.response;

import com.szs.app.domain.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnualIncomeResponse {

  private Long id;

  private String incomeYear;

  private Long IncomeTotal;

  private Long calculatedTax;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private RefundResponse refund;

  private List<DeductionResponse> deductions  = new ArrayList<>();

  private List<IncomeSalaryResponse> incomeSalaries = new ArrayList<>();


  public static AnnualIncomeResponse from(AnnualIncome annualIncome) {
    if (isNull(annualIncome)) {
      return null;
    } else {
      return AnnualIncomeResponse.builder()
                                 .id(annualIncome.getId())
                                 .incomeYear(annualIncome.getIncomeYear())
                                 .IncomeTotal(annualIncome.getIncomeTotal())
                                 .calculatedTax(annualIncome.getCalculatedTax())
                                 .createdAt(annualIncome.getCreatedAt())
                                 .updatedAt(annualIncome.getUpdatedAt())
                                 .refund(RefundResponse.from(annualIncome.getRefund()))
                                 .deductions(annualIncome.getDeductions().stream()
                                                   .map(DeductionResponse::from)
                                                   .collect(Collectors.toList()))
                                 .incomeSalaries(annualIncome.getIncomeSalaries().stream()
                                                         .map(IncomeSalaryResponse::from)
                                                         .collect(Collectors.toList()))
                                 .build();
    }
  }
}