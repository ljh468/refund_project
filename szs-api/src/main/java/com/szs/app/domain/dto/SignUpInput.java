package com.szs.app.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

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
}
