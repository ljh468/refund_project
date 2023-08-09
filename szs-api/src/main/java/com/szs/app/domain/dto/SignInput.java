package com.szs.app.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class SignInput {

  @NotNull
  private final String userId;

  @NotNull
  private final String password;
}
