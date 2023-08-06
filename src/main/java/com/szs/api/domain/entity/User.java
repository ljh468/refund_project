package com.szs.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

  @Id
  @EqualsAndHashCode.Include
  @Column(unique = true, name = "userId")
  private String id;

  private String password;

  private String name;

  private int regNoFront;

  private int regNoBack;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @Builder
  public User(String id, String password, String name, int regNoFront, int regNoBack) {
    this.id = id;
    this.password = password;
    this.name = name;
    this.regNoFront = regNoFront;
    this.regNoBack = regNoBack;
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }

  protected User() {
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("name", name)
        .append("createdAt", createdAt)
        .append("updatedAt", updatedAt)
        .toString();
  }
}
