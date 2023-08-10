package com.szs.app.auth.exception;

import com.szs.app.auth.exception.handler.AbstractErrorCode;

public class AnnualIncomeDataScrapedException extends AbstractException {

  public AnnualIncomeDataScrapedException(String code, String message) {
    super(code, message);
  }

  public AnnualIncomeDataScrapedException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
