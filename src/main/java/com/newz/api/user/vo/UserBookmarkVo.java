package com.newz.api.user.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserBookmarkVo {

  private int id;
  private int userId;
  private String newsUrl;

}
