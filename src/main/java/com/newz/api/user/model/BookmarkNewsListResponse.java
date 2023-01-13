package com.newz.api.user.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BookmarkNewsListResponse {

  private List<BookmarkNewsListModel> news;
  private int total;
  private int page;

}
