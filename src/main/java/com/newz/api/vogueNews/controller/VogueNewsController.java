package com.newz.api.vogueNews.controller;

import com.newz.api.vogueNews.model.vogueNews.dto.VogueNewsDto;
import com.newz.api.vogueNews.service.vogueNews.VogueNewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/news/vogue")
public class VogueNewsController {

    VogueNewsService vogueNewsService;

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
