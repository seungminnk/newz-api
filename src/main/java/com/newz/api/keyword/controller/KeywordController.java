package com.newz.api.keyword.controller;

import com.newz.api.keyword.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

  @Operation(summary = "온보딩 단계에서 하단에 보여줄 예시(고정) 키워드 목록 가져오기")
  @ApiResponses(value = {
      @ApiResponse(
          responseCode = "200",
          description = "9개씩 랜덤한 순서로 반환됨",
          content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = List.class)
              )
          }
      )
  })
  @GetMapping("/fixed-keyword/list")
  public ResponseEntity<List<String>> getFixedKeywordList() {
    return new ResponseEntity<>(keywordService.getFixedKeywords(), HttpStatus.OK);
  }

}
