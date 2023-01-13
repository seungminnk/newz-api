package com.newz.api.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NewzCommonException extends RuntimeException {

  private final HttpStatus httpStatus;
  private String code;

  public NewzCommonException(HttpStatus httpStatus, String code, String message) {
    super(message);

    this.httpStatus = httpStatus;
    this.code = code;
  }

  public NewzCommonException(HttpStatus httpStatus, ErrorCode errorCode) {
    super(errorCode.getMessage());

    this.httpStatus = httpStatus;
    this.code = errorCode.getCode();
  }

}
