package com.szs.api.domain.entity;

import com.szs.api.domain.type.PaymentType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Deduction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @Column(name = "deductionId")
  private Long id;

  @Enumerated(EnumType.STRING)
  private PaymentType paymentType;

  private Long paymentAmount;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "refundId")
  private Refund refund;

  protected Deduction() {
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

