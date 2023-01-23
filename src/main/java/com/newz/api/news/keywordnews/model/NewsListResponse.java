package com.newz.api.news.keywordnews.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsListResponse {

  private List<NewsListModel> news;
  @Schema(description = "전체 데이터(기사) 개수", example = "1521231")
  private long total;
  @Schema(description = "몇 번째 페이지인지", example = "1")
  private int page;

}
