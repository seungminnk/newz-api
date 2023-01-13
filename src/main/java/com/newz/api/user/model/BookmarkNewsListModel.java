package com.newz.api.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookmarkNewsListModel {

  private int bookmarkId;
  private String title;
  private String content;
  private String link;

}
