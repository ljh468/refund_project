package com.szs.app.domain.entity;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "AnnualIncome",
    uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "incomeYear"}))
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AnnualIncome {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @Column(unique = true, name = "annualIncomeId")
  private Long id;

  private String incomeYear;

  private Long IncomeTotal;

  private Long calculatedTax;

  @Setter
  private boolean isRefundCalculated;

  private boolean isDeleted;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId")
  private User user;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "refundId")
  private Refund refund;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "scrapHistoryId")
  private YearEndTaxScrapHistory yearEndTaxScrapHistory;

  @OneToMany(mappedBy = "annualIncome", cascade = CascadeType.ALL)
  private List<Deduction> deductions = new ArrayList<>();

  @OneToMany(mappedBy = "annualIncome", cascade = CascadeType.ALL)
  private List<IncomeSalary> incomeSalaries = new ArrayList<>();

  protected AnnualIncome() {
  }

  @Builder
  public AnnualIncome(String incomeYear, Long calculatedTax, boolean isDeleted) {
    this.incomeYear = incomeYear;
    this.calculatedTax = calculatedTax;
    this.isDeleted = isDeleted;
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  public void addUser(User user) {
    this.user = user;
    user.getAnnualIncomes().add(this);
  }

  public void addYearEndTaxScrapHistory(YearEndTaxScrapHistory scrapHistory) {
    this.yearEndTaxScrapHistory = scrapHistory;
  }

  public void calculateRefund() {
    // 산출세액
    Long calculatedTax = this.getCalculatedTax();

    // 근로소득세액공제금액
    Long workIncomeTaxCredit = Refund.calculatedWorkIncomeCredit(calculatedTax);

    // 특별세액공제금액
    Long specialTaxCredit = Refund.calculatedSpecialCredit(this.getDeductions(), this.IncomeTotal);

    // 표준세액공제금액
    // 단, 표준세액공제금액 = 130,000원이면 특별세액공제금액 = 0 처리
    Long standardTaxCredit = Refund.calculatedStandardCredit(specialTaxCredit);
    specialTaxCredit = standardTaxCredit == 130000 ? 0 : specialTaxCredit;

    // 퇴직연금세액 공제금액
    Long retirementTaxCredit = Refund.calculatedRetirementCredit(this.getDeductions());

    // 결정세액
    Long determinedTax = calculatedTax - workIncomeTaxCredit - specialTaxCredit - standardTaxCredit - retirementTaxCredit;
    determinedTax = determinedTax < 0 ? 0 : determinedTax;

    // 환급 객체 생성
    this.refund = Refund.createRefund(determinedTax, workIncomeTaxCredit, specialTaxCredit, standardTaxCredit, retirementTaxCredit);
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("incomeYear", incomeYear)
        .append("IncomeTotal", IncomeTotal)
        .append("calculatedTax", calculatedTax)
        .append("isRefundCalculated", isRefundCalculated)
        .append("isDeleted", isDeleted)
        .append("createdAt", createdAt)
        .append("updatedAt", updatedAt)
        .toString();
  }

  public void updateIncomeTotal(Long incomeTotal) {
    this.IncomeTotal = incomeTotal;
  }
}
