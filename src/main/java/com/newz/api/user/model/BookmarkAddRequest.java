package com.newz.api.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkAddRequest {

  private int userId;
  private String newsUrl;

}
