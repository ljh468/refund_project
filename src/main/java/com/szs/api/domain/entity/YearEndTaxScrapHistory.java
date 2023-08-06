package com.szs.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "ScrapHistory")
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class YearEndTaxScrapHistory {

  @Id
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

  protected YearEndTaxScrapHistory() {
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
