package com.szs.app.auth.exception;

import lombok.AllArgsConstructor;

import java.text.MessageFormat;

@AllArgsConstructor
public class AnnualIncomeDataAlreadyExistsException extends RuntimeException {

  private final String userId;

  private final String incomeYear;

  @Override
  public String getMessage() {
    return MessageFormat.format("Income data for the year ''{0}'' of ''{1}'' already exists", incomeYear, userId);
  }
}
