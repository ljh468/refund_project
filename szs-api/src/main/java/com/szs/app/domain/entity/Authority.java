package com.szs.app.domain.entity;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;

@Entity
@Table(name = "Authority")
@Getter
@Builder
@AllArgsConstructor
public class Authority {

  @Id@Column(name = "authorityName")
  private String authorityName;

  protected Authority() {
  }

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("authorityName", authorityName)
        .toString();
  }
}