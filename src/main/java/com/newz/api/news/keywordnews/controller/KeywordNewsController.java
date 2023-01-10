package com.newz.api.news.keywordnews.controller;

import com.newz.api.news.keywordnews.model.NewsListResponse;
import com.newz.api.news.keywordnews.service.KeywordNewsService;
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

  @GetMapping("/list")
  public ResponseEntity<NewsListResponse> getNewsListByQuery(
      @RequestParam("query") String query,
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "limit", required = false, defaultValue = "3") int limit) {
    return new ResponseEntity<>(keywordNewsService.getNewsListByQuery(query, page, limit), HttpStatus.OK);
  }

}
