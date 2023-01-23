package com.newz.api.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkAddRequest {

  @Schema(accessMode = AccessMode.READ_ONLY)
  private int userId;
  @Schema(description = "삭제할 북마크 기사 url", example = "https://n.news.naver.com/mnews/article/055/0001025795?sid=102")
  private String newsUrl;

}
