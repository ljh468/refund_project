package com.szs.app.exception;

import com.szs.app.exception.handler.AbstractErrorCode;

public class BedCredentialsException extends AbstractException {

  public BedCredentialsException(String code, String message) {
    super(code, message);
  }

  public BedCredentialsException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
