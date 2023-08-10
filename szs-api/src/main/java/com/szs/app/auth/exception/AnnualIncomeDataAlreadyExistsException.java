package com.szs.app.auth.exception;

import com.szs.app.auth.exception.handler.AbstractErrorCode;

public class AnnualIncomeDataAlreadyExistsException extends AbstractException {

  public AnnualIncomeDataAlreadyExistsException(String code, String message) {
    super(code, message);
  }

  public AnnualIncomeDataAlreadyExistsException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
