package com.szs.app.auth.exception;

import com.szs.app.auth.exception.handler.AbstractErrorCode;

public class UserNotFoundException extends AbstractException {

  public UserNotFoundException(String code, String message) {
    super(code, message);
  }

  public UserNotFoundException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
