package com.newz.api.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler {

  @ExceptionHandler(value = {Exception.class})
  protected ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse response = ErrorResponse.builder()
        .code("INTERNAL_SERVER_ERROR")
        .message(e.getMessage())
        .build();

    log.error(e.getMessage());

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {NewzCommonException.class})
  protected ResponseEntity<ErrorResponse> handleNewzCommonException(NewzCommonException e) {
    ErrorResponse response = ErrorResponse.builder()
        .code(e.getCode())
        .message(e.getMessage())
        .build();

    log.error(e.getMessage());

    return new ResponseEntity<>(response, e.getHttpStatus());
  }

}
