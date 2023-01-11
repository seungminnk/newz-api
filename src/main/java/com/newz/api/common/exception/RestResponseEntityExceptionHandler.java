package com.newz.api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse response = ErrorResponse.builder()
        .message(e.getMessage())
        .build();

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {NewzCommonException.class})
  protected ResponseEntity<ErrorResponse> handleNewzCommonException(NewzCommonException e) {
    ErrorResponse response = ErrorResponse.builder()
        .message(e.getMessage())
        .build();

    return new ResponseEntity<>(response, e.getHttpStatus());
  }

}
