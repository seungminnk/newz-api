package com.newz.api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  INVALID_REQUEST_DATA("INVALID_REQUEST_DATA", "요청 파라미터를 다시 확인하세요."),
  ONLY_CAN_SAVE_UP_TO_NINE_KEYWORD("ONLY_CAN_SAVE_UP_TO_NINE_KEYWORD", "키워드는 총 9개까지 저장 가능합니다."),
  USER_NOT_FOUND("USER_NOT_FOUND", "해당 사용자는 존재하지 않습니다."),
  EXPIRED_TOKEN("EXPIRED_TOKEN", "만료된 토큰입니다."),
  INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 토큰입니다."),
  TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", "인증 토큰이 필요합니다.");

  private String code;
  private String message;

}
