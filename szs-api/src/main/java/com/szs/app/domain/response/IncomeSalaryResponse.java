package com.szs.app.domain.response;

import com.szs.app.domain.entity.IncomeSalary;
import lombok.*;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncomeSalaryResponse {

  private Long id;

  private String incomeDetail;

  private String incomeType;

  private String companyName;

  private String companyRegNo;

  private Long paymentTotal;

  private LocalDate paymentDate;

  private LocalDate workStartDate;

  private LocalDate workEndDate;

  public static IncomeSalaryResponse from(IncomeSalary incomeSalary) {
    if (isNull(incomeSalary)) {
      return null;
    }
    return IncomeSalaryResponse.builder()
                               .id(incomeSalary.getId())
                               .incomeDetail(incomeSalary.getIncomeDetail())
                               .incomeType(incomeSalary.getIncomeType())
                               .companyName(incomeSalary.getCompanyName())
                               .companyRegNo(incomeSalary.getCompanyRegNo())
                               .paymentTotal(incomeSalary.getPaymentTotal())
                               .paymentDate(incomeSalary.getPaymentDate())
                               .workStartDate(incomeSalary.getWorkStartDate())
                               .workEndDate(incomeSalary.getWorkEndDate())
                               .build();
  }
}
