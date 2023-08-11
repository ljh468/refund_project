package com.szs.app.exception;

import com.szs.app.exception.handler.AbstractErrorCode;

public class AnnualIncomeDataScrapedException extends AbstractException {

  public AnnualIncomeDataScrapedException(String code, String message) {
    super(code, message);
  }

  public AnnualIncomeDataScrapedException(AbstractErrorCode errorCode, String message) {
    super(errorCode, message);
  }
}
