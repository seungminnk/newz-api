package com.newz.api.user.model;

import lombok.Getter;

@Getter
public class BookmarkAddRequest {

  private int userId;
  private String newsUrl;

}
