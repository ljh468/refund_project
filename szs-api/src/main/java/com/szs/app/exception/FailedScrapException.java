package com.szs.app.exception;

import com.szs.app.exception.handler.AbstractErrorCode;

public class FailedScrapException extends AbstractException {

  public FailedScrapException(String code, String message) {
    super(code, message);
  }

  public FailedScrapException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
