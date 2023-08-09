package com.szs.app.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AllowableUser")
@Builder
@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AllowableUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  @Column(name = "allowableUserId")
  private Long id;

  private String name;

  private String regNoFront;

  private String regNoBack;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

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
