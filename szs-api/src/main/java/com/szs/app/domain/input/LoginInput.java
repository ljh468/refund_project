package com.szs.app.domain.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class LoginInput {

  @NotNull
  @Schema(example = "szsUserId01")
  private final String userId;

  @NotNull
  @Schema(example = "szsPassword01")
  private final String password;

  @Override
  public String toString(){
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("userId", userId)
        .toString();
  }
}
