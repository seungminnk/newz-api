package com.newz.api.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NewzCommonException extends RuntimeException {

  private final HttpStatus httpStatus;

  public NewzCommonException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
