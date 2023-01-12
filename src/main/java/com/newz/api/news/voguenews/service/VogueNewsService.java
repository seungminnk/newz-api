package com.newz.api.news.voguenews.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.newz.api.news.voguenews.dto.VogueNewsDataDto;
import com.newz.api.news.voguenews.dto.VogueNewsDto;
import com.newz.api.news.voguenews.dto.VogueNewsOnlyKeyword;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
@AllArgsConstructor
public class VogueNewsService {

    final Gson gson = new Gson();
    final RestTemplate restTemplate = new RestTemplate();
    final String googleTrendsApiUrl = "https://trends.google.com/trends/api/dailytrends?hl=ko&tz=-540&geo=KR&ns=15";

    public List<VogueNewsOnlyKeyword> retrieveOnlyKeywordsFromGoogleTrendApi(){

        List<VogueNewsOnlyKeyword> vogueNewsOnlyKeywordList = new Stack<>();

        VogueNewsDto vogueNewsDto = retrieveDataFromGoogleTrendsApi();

        for(VogueNewsDataDto vogueNewsDataDto : vogueNewsDto.getVogueNewsDataDtoList()){
            vogueNewsOnlyKeywordList.add(
                new VogueNewsOnlyKeyword(
                    vogueNewsDataDto.getKeyword()
                ).unContaminatedKeyword()
            );
        }

        return vogueNewsOnlyKeywordList;
    }

    public VogueNewsDto retrieveDataFromGoogleTrendsApi() {

        String responseGoogleTrendsText = restTemplate.getForEntity(googleTrendsApiUrl, String.class).getBody();
        String selectGoogleTrendsText = responseGoogleTrendsText.split(",", 2)[1];
        String decontaminationGoogleTrendsText = selectGoogleTrendsText.replace("\n", "");

        JsonObject googleTrendsJsonObject = gson.fromJson(decontaminationGoogleTrendsText, JsonObject.class);

        VogueNewsDto vogueNewsDto = VogueNewsDto.fromJsonObject("google", googleTrendsJsonObject);

        return vogueNewsDto;
    }

}
