package com.szs.api.domain.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "AnnualIncome")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AnnualIncome {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @Column(unique = true, name = "annualIncomeId")
  private Long id;

  @Column(nullable = false, unique = true)
  private String incomeYear;

  private Long IncomeTotal;

  private Long calculatedTax;

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
  private List<Deduction> deductions  = new ArrayList<>();

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

  public void addUser(User user){
    this.user = user;
    user.getAnnualIncomes().add(this);
  }

  public void addYearEndTaxScrapHistory(YearEndTaxScrapHistory scrapHistory){
    this.yearEndTaxScrapHistory = scrapHistory;
    scrapHistory.addAnnualIncome(this);
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("incomeYear", incomeYear)
        .append("IncomeTotal", IncomeTotal)
        .append("calculatedTax", calculatedTax)
        .append("isDeleted", isDeleted)
        .append("createdAt", createdAt)
        .append("updatedAt", updatedAt)
        .toString();
  }

  public void updateIncomeTotal(Long incomeTotal) {
    this.IncomeTotal = incomeTotal;
  }
}
