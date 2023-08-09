package com.szs.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "Refund")
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Refund {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @Column(unique = true, name = "refundId")
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

  protected Refund() {
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
