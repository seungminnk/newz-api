package com.newz.api.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  ONLY_CAN_SAVE_UP_TO_NINE_KEYWORD("ONLY_CAN_SAVE_UP_TO_NINE_KEYWORD", "키워드는 총 9개까지 저장 가능합니다."),
  USER_NOT_FOUND("USER_NOT_FOUND", "해당 사용자는 존재하지 않습니다.");

  private String code;
  private String message;

}
