package com.szs.app.auth.exception;

import com.szs.app.auth.exception.handler.AbstractErrorCode;

public class RegNumberMismatchException extends AbstractException {

  public RegNumberMismatchException(String code, String message) {
    super(code, message);
  }

  public RegNumberMismatchException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
