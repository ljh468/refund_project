package com.szs.app.domain.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class SignUpInput {

  @NotNull
  private final String userId;

  @NotNull
  private final String password;

  @NotNull
  private final String name;

  @NotNull
  //FIXME 주민등록 번호 validation 추가
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
