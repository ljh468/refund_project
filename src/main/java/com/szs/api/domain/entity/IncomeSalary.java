package com.szs.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "IncomeSalary")
@AllArgsConstructor
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
  @JoinColumn(name = "refundId")
  private Refund refund;

  protected IncomeSalary() {
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
