package com.newz.api.keyword.controller;

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

  @GetMapping("/fixed-keyword/list")
  public ResponseEntity<List<String>> getFixedKeywordList() {
    List<String> result = new ArrayList<>();
    result.add("구글");
    result.add("오늘의 날씨");
    result.add("축구");
    result.add("테슬라");

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
