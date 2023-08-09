package com.szs.app.domain.entity;

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
import java.util.Set;

@Entity
@Table(name = "Users")
@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

  @Id
  @EqualsAndHashCode.Include
  @Column(unique = true, name = "userId")
  private String id;

  private String password;

  private String name;

  private String regNoFront;

  private String regNoBack;

  private boolean isActivated;

  private boolean isDeleted;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<AnnualIncome> annualIncomes = new ArrayList<>();

  @ManyToMany
  @JoinTable(name = "userAuthority",
      joinColumns = {@JoinColumn(name = "userId", referencedColumnName = "userId")},
      inverseJoinColumns = {@JoinColumn(name = "authorityName", referencedColumnName = "authorityName")})
  private Set<Authority> authorities;

  protected User() {
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", id)
        .append("name", name)
        .append("isActivated", isActivated)
        .append("isDeleted", isDeleted)
        .append("createdAt", createdAt)
        .append("updatedAt", updatedAt)
        .toString();
  }
}
