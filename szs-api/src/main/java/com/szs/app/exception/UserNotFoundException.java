package com.szs.app.exception;

import com.szs.app.exception.handler.AbstractErrorCode;

public class UserNotFoundException extends AbstractException {

  public UserNotFoundException(String code, String message) {
    super(code, message);
  }

  public UserNotFoundException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
