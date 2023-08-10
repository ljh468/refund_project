package com.szs.app.auth.exception;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;

@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {

  private final String userId;

  @Override
  public String getMessage() {
    return MessageFormat.format("not found with userId ''{0}''", userId);
  }
}
