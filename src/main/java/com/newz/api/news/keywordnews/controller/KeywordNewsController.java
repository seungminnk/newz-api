package com.newz.api.news.keywordnews.controller;

import com.newz.api.news.keywordnews.model.NewsListResponse;
import com.newz.api.news.keywordnews.service.KeywordNewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/news")
public class KeywordNewsController {

  @Autowired
  private KeywordNewsService keywordNewsService;

  @Operation(summary = "검색어(키워드)에 해당하는 뉴스 기사 데이터 반환")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "limit 값 만큼 검색어에 맞는 뉴스 기사 제목/요약내용/원문링크 데이터 반환",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NewsListResponse.class)
              )
          }
      )
  })
  @GetMapping("/list")
  public ResponseEntity<NewsListResponse> getNewsListByQuery(
      @RequestParam("query") @Schema(description = "검색어", example = "플러터") String query,
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "limit", required = false, defaultValue = "3") int limit) {
    return new ResponseEntity<>(keywordNewsService.getNewsListByQuery(query, page, limit), HttpStatus.OK);
  }

}
