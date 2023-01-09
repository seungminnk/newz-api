package com.newz.api.vogueNews.controller;

import com.newz.api.vogueNews.model.vogueNews.dto.VogueNewsDto;
import com.newz.api.vogueNews.service.vogueNews.VogueNewsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/news/vogue")
public class VogueNewsController {

    VogueNewsService vogueNewsService;

    @GetMapping("")
    VogueNewsDto getRanks() {
        return vogueNewsService.retrieveDataFromGoogleTrendsApi();
    }
}
