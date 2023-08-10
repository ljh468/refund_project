package com.szs.app.domain.input;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class LoginInput {

  @NotNull
  private final String userId;

  @NotNull
  private final String password;

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("userId", userId)
        .toString();
  }
}
