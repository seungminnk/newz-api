package com.newz.api.news.keywordnews.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsListResponse {

  private List<NewsListModel> news;
  private long total;
  private int page;

}
