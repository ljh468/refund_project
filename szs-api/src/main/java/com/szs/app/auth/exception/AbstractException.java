package com.szs.app.auth.exception;

import com.szs.app.auth.exception.handler.AbstractErrorCode;
import lombok.Getter;

@Getter
public class AbstractException extends RuntimeException {

  private String code;

  private String message;

  private AbstractException(String message) {
    super(message);
    this.message = message;
  }

  protected AbstractException(String code, String message) {
    this(message);
    this.code = code;
  }

  protected AbstractException(AbstractErrorCode errorCode, String message) {
    this(message);
    this.code = errorCode.getErrCode();
  }
}
