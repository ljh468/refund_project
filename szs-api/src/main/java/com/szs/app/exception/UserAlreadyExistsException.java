package com.szs.app.exception;

import com.szs.app.exception.handler.AbstractErrorCode;

public class UserAlreadyExistsException extends AbstractException {

  public UserAlreadyExistsException(String code, String message) {
    super(code, message);
  }

  public UserAlreadyExistsException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
