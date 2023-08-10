package com.szs.app.domain.response;

import com.szs.app.domain.entity.AnnualIncome;
import com.szs.app.domain.entity.Refund;
import lombok.*;

import java.time.LocalDateTime;

import static java.util.Objects.isNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefundResponse {

  private Long id;

  private String userName;

  private String incomeYear;

  private double determinedTax;

  private double retirePensionCredit;

  private boolean isCompleted;

  public static RefundResponse from(Refund refund) {
    if (isNull(refund)) {
      return null;
    }
    return RefundResponse.builder()
                         .id(refund.getId())
                         .determinedTax(refund.getDeterminedTax())
                         .retirePensionCredit(refund.getRetireTaxCredit())
                         .isCompleted(refund.isCompleted())
                         .build();
  }

  public static RefundResponse from(AnnualIncome annualIncome) {
    if (isNull(annualIncome)) {
      return null;
    }
    return RefundResponse.builder()
                         .id(annualIncome.getRefund().getId())
                         .userName(annualIncome.getUser().getName())
                         .incomeYear(annualIncome.getIncomeYear())
                         .determinedTax(annualIncome.getRefund().getDeterminedTax())
                         .retirePensionCredit(annualIncome.getRefund().getRetireTaxCredit())
                         .isCompleted(annualIncome.getRefund().isCompleted())
                         .build();
  }

}
