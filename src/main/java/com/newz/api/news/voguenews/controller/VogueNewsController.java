package com.newz.api.news.voguenews.controller;

import com.newz.api.news.voguenews.dto.VogueNewsDto;
import com.newz.api.news.voguenews.dto.VogueNewsOnlyKeyword;
import com.newz.api.news.voguenews.service.VogueNewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/news/vogue")
public class VogueNewsController {

    VogueNewsService vogueNewsService;

    @GetMapping("/keywords")
    List<VogueNewsOnlyKeyword> getOnlyKeywords(){
        return vogueNewsService.retrieveOnlyKeywordsFromGoogleTrendApi();
    }

    @Operation(summary = "인기 검색어와 해당 검색어에 따르는 기사들 데이터 반환")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "구글 트렌드 실시간 인기 검색어 반환",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = VogueNewsDto.class)
                    )
                }
            )
        }
    )
    @GetMapping("")
    VogueNewsDto getRanks() {
        return vogueNewsService.retrieveDataFromGoogleTrendsApi();
    }
}
