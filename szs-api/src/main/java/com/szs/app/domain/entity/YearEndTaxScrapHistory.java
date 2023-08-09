package com.szs.app.domain.entity;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "YearEndTaxScrapHistory")
@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class YearEndTaxScrapHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @Column(unique = true, name = "scrapId")
  private Long id;

  private String appVer;

  private String errMsg;

  private String svcCd;

  private String company;

  private String hostNm;

  private LocalDateTime workerResDt;

  private LocalDateTime workerReqDt;

  private LocalDateTime createdAt;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "userId")
  private User user;

  @OneToOne(mappedBy = "yearEndTaxScrapHistory", cascade = CascadeType.ALL)
  private AnnualIncome annualIncome;

  protected YearEndTaxScrapHistory() {
  }

  public void addUser(User user) {
    this.user = user;
  }

  public void addAnnualIncome(AnnualIncome annualIncome) {
    this.annualIncome = annualIncome;
  }
  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("appVer", appVer)
        .append("errMsg", errMsg)
        .append("svcCd", svcCd)
        .append("company", company)
        .append("hostNm", hostNm)
        .append("workerResDt", workerResDt)
        .append("workerReqDt", workerReqDt)
        .append("createdAt", createdAt)
        .toString();
  }
}
