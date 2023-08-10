package com.szs.app.domain.entity;

import com.szs.app.domain.type.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.szs.util.TypeConverter.doubleToLong;
import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "Refund")
@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Refund {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @Column(unique = true, name = "refundId")
  private Long id;

  private Long determinedTax;

  private Long retireTaxCredit;

  private Long workIncomeTaxCredit;

  private Long specialTaxCredit;

  private Long standardTaxCredit;

  private boolean isCompleted;

  private boolean isDeleted;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId")
  private User user;

  protected Refund() {
  }

  public static Refund createRefund(Long determinedTax,
                                    Long workIncomeTaxCredit,
                                    Long specialTaxCredit,
                                    Long standardTaxCredit,
                                    Long retirementTaxCredit) {
    LocalDateTime now = LocalDateTime.now();
    return Refund.builder()
                 .determinedTax(determinedTax)
                 .retireTaxCredit(retirementTaxCredit)
                 .workIncomeTaxCredit(workIncomeTaxCredit)
                 .specialTaxCredit(specialTaxCredit)
                 .standardTaxCredit(standardTaxCredit)
                 .isCompleted(false)
                 .isDeleted(false)
                 .createdAt(now)
                 .updatedAt(now)
                 .build();
  }

  public static Long calculatedWorkIncomeCredit(double calculatedTax) {
    return doubleToLong(calculatedTax * 0.55);
  }

  public static Long calculatedSpecialCredit(List<Deduction> deductions, Long IncomeTotal) {
    long specialTaxCredit = 0L;
    for (Deduction deduction : deductions) {
      switch (deduction.getPaymentType()) {
        // 보험료공제금액 = 보험료납입금액 * 12%
        case INSURANCE:
          specialTaxCredit += doubleToLong(deduction.getPaymentAmount() * 0.12);
          break;
        // 의료비공제금액 = (의료비납입금액 - 총급여 * 3%) * 15%
        case MEDICAL:
          double medicalTaxCredit = (deduction.getPaymentAmount() - (IncomeTotal * 0.03)) * 0.15;
          specialTaxCredit += doubleToLong(medicalTaxCredit > 0 ? medicalTaxCredit : 0);
          break;
        // 교육비공제금액 = 교육비납입금액 * 15%
        case EDUCATION:
          specialTaxCredit += doubleToLong(deduction.getPaymentAmount() * 0.15);
          break;
        // 기부금공제금액 = 기부금납입금액 * 15%
        case DONATION:
          specialTaxCredit += doubleToLong(deduction.getPaymentAmount() * 0.15);
          break;
      }
    }
    return specialTaxCredit;
  }

  public static Long calculatedStandardCredit(double specialTaxDeduction) {
    return specialTaxDeduction >= 130000L ? 0L : 130000L;
  }

  public static Long calculatedRetirementCredit(List<Deduction> deductions) {
    long retirementTaxCredit = 0;
    for (Deduction deduction : deductions) {
      if (deduction.getPaymentType().equals(PaymentType.RETIREMENT)) {
        return doubleToLong(deduction.getPaymentAmount() * 0.15);
      }
    }
    return retirementTaxCredit;
  }

  public void addUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("determinedTax", determinedTax)
        .append("retirePensionTaxCredit", retireTaxCredit)
        .append("workIncomeTaxCredit", workIncomeTaxCredit)
        .append("specialTaxCredit", specialTaxCredit)
        .append("standardTaxCredit", standardTaxCredit)
        .append("isCompleted", isCompleted)
        .append("isDeleted", isDeleted)
        .append("createdAt", createdAt)
        .append("updatedAt", updatedAt)
        .toString();
  }
}
