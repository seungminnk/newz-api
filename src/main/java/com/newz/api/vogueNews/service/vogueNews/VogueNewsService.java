package com.newz.api.vogueNews.service.vogueNews;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.newz.api.vogueNews.model.vogueNews.dto.VogueNewsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class VogueNewsService {

    final Gson gson = new Gson();
    final RestTemplate restTemplate = new RestTemplate();
    final String googleTrendsApiUrl = "https://trends.google.com/trends/api/dailytrends?hl=ko&tz=-540&geo=KR&ns=15";

    public VogueNewsDto getRanksJavascript() {
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        String responseGoogleTrendsText = restTemplate.getForEntity(googleTrendsApiUrl, String.class).getBody();
        String selectGoogleTrendsText = responseGoogleTrendsText.split(",", 2)[1];
        String decontaminationGoogleTrendsText = selectGoogleTrendsText.replace("\n", "");

        JsonObject googleTrendsJsonObject = gson.fromJson(decontaminationGoogleTrendsText, JsonObject.class);

        VogueNewsDto vogueNewsDto = VogueNewsDto.fromJsonObject("google", googleTrendsJsonObject);

        return vogueNewsDto;
    }

}
