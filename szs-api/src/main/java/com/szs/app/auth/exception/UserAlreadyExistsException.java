package com.szs.app.auth.exception;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;

@AllArgsConstructor
public class UserAlreadyExistsException extends RuntimeException {

  private final String userId;

  @Override
  public String getMessage() {
    return MessageFormat.format("already exists with userId ''{0}''", userId);
  }
}
