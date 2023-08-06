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
@Table(name = "Refund")
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Refund {

  @Id
  @EqualsAndHashCode.Include
  @Column(unique = true, name = "refundId")
  private Long id;

  private Long calculatedTax;

  private Long determinedTax;

  private Long retirePensionCredit;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId")
  private User user;

  @OneToOne(fetch = LAZY)
  @JoinColumn(name = "scrapHistoryId")
  private ScrapHistory scrapHistory;

  @OneToMany(mappedBy = "refund", cascade = CascadeType.ALL)
  private List<Deduction> deductions  = new ArrayList<>();

  @OneToMany(mappedBy = "refund", cascade = CascadeType.ALL)
  private List<IncomeSalary> incomeSalaries = new ArrayList<>();

  protected Refund() {
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("calculatedTax", calculatedTax)
        .append("determinedTax", determinedTax)
        .append("retirePensionCredit", retirePensionCredit)
        .append("createdAt", createdAt)
        .append("updatedAt", updatedAt)
        .toString();
  }
}
