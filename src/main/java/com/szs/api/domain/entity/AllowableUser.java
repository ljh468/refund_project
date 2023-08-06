package com.szs.api.domain.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "AllowableUser")
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AllowableUser {

  @Id
  @EqualsAndHashCode.Include
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "allowableUserId")
  private Long id;

  private String name;

  private int regNoFront;

  private int regNoBack;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @Builder
  public AllowableUser(String name, Integer regNoFront, Integer regNoBack){
    this.name = name;
    this.regNoFront = regNoFront;
    this.regNoBack = regNoBack;
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  protected AllowableUser() {
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("name", name)
        .append("createdDate", createdAt)
        .append("updatedDate", updatedAt)
        .toString();
  }
}
