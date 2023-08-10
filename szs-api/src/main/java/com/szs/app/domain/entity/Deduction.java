package com.szs.app.domain.entity;

import com.szs.app.domain.type.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Deduction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @Column(name = "deductionId")
  private Long id;

  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  private Double paymentAmount;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "annualIncomeId")
  private AnnualIncome annualIncome;

  protected Deduction() {
  }

  @Builder
  public Deduction(PaymentType paymentType, Double paymentAmount) {
    this.paymentType = paymentType;
    this.paymentAmount = paymentAmount;
  }

  public void addAnnualIncome(AnnualIncome annualIncome) {
    this.annualIncome = annualIncome;
    annualIncome.getDeductions().add(this);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("paymentType", paymentType)
        .append("paymentAmount", paymentAmount)
        .toString();
  }
}

