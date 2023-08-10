package com.szs.app.auth.exception.handler;

public enum ErrorCode implements AbstractErrorCode {

  E0000("E0000", "user not found"),

  E0001("E0001", "annualIncome data already exist"),

  E0002("E0002", "bad credentials"),

  E0003("E0003", "scrap failed"),

  E0004("E0004", "username or registration number not allowed"),

  E0005("E0005", "user not found");

  private String errCode;

  private String message;

  ErrorCode(String errCode, String message) {
    this.errCode = errCode;
    this.message = message;
  }

  @Override
  public String getErrCode() {
    return this.errCode;
  }

  @Override
  public String getMessage() {
    return this.message;
  }
}
