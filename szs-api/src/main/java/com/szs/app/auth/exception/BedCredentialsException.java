package com.szs.app.auth.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BedCredentialsException extends RuntimeException {

  public BedCredentialsException(String message) {
    super(message);
  }

  public BedCredentialsException(String message, Throwable cause) {
    super(message, cause);
  }
}
