package com.szs.api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDate;

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

  private LocalDate createdDate;

  private LocalDate updatedDate;

  public User() {
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("name", name)
        .append("createdDate", createdDate)
        .append("updatedDate", updatedDate)
        .toString();
  }
}
