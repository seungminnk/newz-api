package com.newz.api.news.keywordnews.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NewsListModel {

  @Schema(description = "기사 제목", example = "커피빈, 제품 가격 인상")
  private String title;
  @Schema(description = "기사 내용", example = "커피 전문점 '커피빈'은 3일부터 우유가 포함된 음료의 가격을 200원씩 인상하며 카페라떼(s)를 기존 5600원에서 5800원에, 바닐라라떼(s)는 6100원에서 6300원에 구매할 수 있다.")
  private String content;
  @Schema(description = "기사 원문 링크", example = "https://n.news.naver.com/mnews/article/055/0001025795?sid=102")
  private String link;

}
