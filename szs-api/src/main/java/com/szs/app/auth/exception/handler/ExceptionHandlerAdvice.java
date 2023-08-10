package com.szs.app.auth.exception.handler;

import com.szs.app.auth.exception.FailedScrapException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

  // @ExceptionHandler(UserNotFoundException.class)
  // public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
  //   ErrorResponse errorResponse = new ErrorResponse(new ErrorResponse.Errors("-1", "The requested value is not a drawable user."));
  //   return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  // }

  @ExceptionHandler(FailedScrapException.class)
  public ResponseEntity<ErrorResponse> handleFailedScrapException(FailedScrapException ex) {
    ErrorResponse errorResponse = new ErrorResponse(new ErrorResponse.Errors(ex.getCode(), "Your custom message here"));
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR); // or any other suitable status code
  }
}