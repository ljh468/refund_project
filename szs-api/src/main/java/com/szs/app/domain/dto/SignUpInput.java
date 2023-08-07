package com.szs.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class SignUpInput {

  private final String userId;

  private final String password;

  private final String name;

  private final String regNo;

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("userId", userId)
        .append("name", name)
        .append("regNo", regNo)
        .toString();
  }
}
