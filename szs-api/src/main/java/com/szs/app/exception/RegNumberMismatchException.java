package com.szs.app.exception;

import com.szs.app.exception.handler.AbstractErrorCode;

public class RegNumberMismatchException extends AbstractException {

  public RegNumberMismatchException(String code, String message) {
    super(code, message);
  }

  public RegNumberMismatchException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
