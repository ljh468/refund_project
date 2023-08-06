package com.szs.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "IncomeSalary")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IncomeSalary {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @Column(name = "incomeSalaryId")
  private Long id;

  private String incomeDetail;

  private String incomeType;

  private String companyName;

  private String companyRegNo;

  private Long paymentTotal;

  private LocalDate paymentDate;

  private LocalDate workStartDate;

  private LocalDate workEndDate;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "annualIncomeId")
  private AnnualIncome annualIncome;

  protected IncomeSalary() {
  }

  @Builder
  public IncomeSalary(String incomeDetail, String incomeType, String companyName, String companyRegNo, Long paymentTotal, LocalDate paymentDate, LocalDate workStartDate, LocalDate workEndDate) {
    this.incomeDetail = incomeDetail;
    this.incomeType = incomeType;
    this.companyName = companyName;
    this.companyRegNo = companyRegNo;
    this.paymentTotal = paymentTotal;
    this.paymentDate = paymentDate;
    this.workStartDate = workStartDate;
    this.workEndDate = workEndDate;
  }

  public void addAnnualIncome(AnnualIncome annualIncome) {
    this.annualIncome = annualIncome;
    annualIncome.getIncomeSalaries().add(this);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("incomeDetail", incomeDetail)
        .append("incomeType", incomeType)
        .append("companyName", companyName)
        .append("companyRegNo", companyRegNo)
        .append("paymentTotal", paymentTotal)
        .append("paymentDate", paymentDate)
        .append("workStartDate", workStartDate)
        .append("workEndDate", workEndDate)
        .toString();
  }
}
