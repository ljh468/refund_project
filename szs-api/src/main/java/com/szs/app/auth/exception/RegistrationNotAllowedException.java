package com.szs.app.auth.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RegistrationNotAllowedException extends RuntimeException {

  public RegistrationNotAllowedException(String message) {
    super(message);
  }
}
