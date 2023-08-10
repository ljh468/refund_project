package com.szs.app.auth.exception;

import com.szs.app.auth.exception.handler.AbstractErrorCode;

public class FailedScrapException extends AbstractException {

  public FailedScrapException(String code, String message) {
    super(code, message);
  }

  public FailedScrapException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
