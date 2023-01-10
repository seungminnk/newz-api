package com.newz.api.user.vo;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserKeywordVo {

  private int id;
  private ZonedDateTime createDtm;
  private int userId;
  private String keyword;

}
