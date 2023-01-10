package com.newz.api.news.keywordnews.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsListModel {

  private String title;
  private String content;
  private String link;

}
