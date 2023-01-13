package com.newz.api.keyword.controller;

import com.newz.api.keyword.service.KeywordService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class KeywordController {

  private KeywordService keywordService;

  @GetMapping("/fixed-keyword/list")
  public ResponseEntity<List<String>> getFixedKeywordList() {
    return new ResponseEntity<>(keywordService.getFixedKeywords(), HttpStatus.OK);
  }

}
