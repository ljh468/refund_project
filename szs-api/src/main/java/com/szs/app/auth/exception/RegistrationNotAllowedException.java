package com.szs.app.auth.exception;

import com.szs.app.auth.exception.handler.AbstractErrorCode;

public class RegistrationNotAllowedException extends AbstractException {

  public RegistrationNotAllowedException(String code, String message) {
    super(code, message);
  }

  public RegistrationNotAllowedException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
