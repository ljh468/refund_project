package com.szs.app.auth.exception.handler;

import com.szs.app.auth.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.szs.app.auth.exception.BedCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

  @ExceptionHandler(AnnualIncomeDataScrapedException.class)
  public ResponseEntity<ErrorResponse> handleAnnualIncomeDataAlreadyExistsException(AnnualIncomeDataScrapedException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BedCredentialsException.class)
  public ResponseEntity<ErrorResponse> handleBadCredentialsException(BedCredentialsException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RegistrationNotAllowedException.class)
  public ResponseEntity<ErrorResponse> handleRegistrationNotAllowedException(RegistrationNotAllowedException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(RegNumberMismatchException.class)
  public ResponseEntity<ErrorResponse> handleRegNumberMismatchException(RegNumberMismatchException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserAlreadyExistsException.class)
  public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(FailedScrapException.class)
  public ResponseEntity<ErrorResponse> handleFailedScrapException(FailedScrapException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getCode(), exception.getMessage());
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}