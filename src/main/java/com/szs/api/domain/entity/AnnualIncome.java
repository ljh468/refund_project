package com.szs.api.domain.entity;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AnnualIncome {

  @Id
  @EqualsAndHashCode.Include
  @Column(unique = true, name = "annualIncomeId")
  private Long id;

  private Long determinedTax;

  private Long retirePensionCredit;

  private boolean isCompleted;

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

  @OneToMany(mappedBy = "refund", cascade = CascadeType.ALL)
  private List<Deduction> deductions  = new ArrayList<>();

  @OneToMany(mappedBy = "refund", cascade = CascadeType.ALL)
  private List<IncomeSalary> incomeSalaries = new ArrayList<>();

  protected AnnualIncome() {
  }

  public void addUser(User user){
    this.user = user;
    user.getAnnualIncomes().add(this);
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("determinedTax", determinedTax)
        .append("retirePensionCredit", retirePensionCredit)
        .append("isCompleted", isCompleted)
        .append("isDeleted", isDeleted)
        .append("createdAt", createdAt)
        .append("updatedAt", updatedAt)
        .toString();
  }
}
