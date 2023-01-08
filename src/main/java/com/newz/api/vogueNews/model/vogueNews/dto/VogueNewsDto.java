package com.newz.api.vogueNews.model.vogueNews.dto;

import com.google.gson.JsonObject;
import lombok.Getter;

import java.util.List;

@Getter
public class VogueNewsDto {
    String domain;
    String date;
    List<VogueNewsDataDto> data;

    public VogueNewsDto(String domain, String date, List<VogueNewsDataDto> data) {
        this.domain = domain;
        this.date = date;
        this.data = data;
    }

    public static VogueNewsDto fromJsonObject(String domain, JsonObject googleTrendsJsonObject){

        String date = String.valueOf(googleTrendsJsonObject.get("default").getAsJsonObject().get("trendingSearchesDays").getAsJsonArray().get(0).getAsJsonObject().get("date"));

        return new VogueNewsDto(
            domain,
            date,
            VogueNewsDataDto.fromJsonObject(googleTrendsJsonObject)
        );
    }

    VogueNewsDto sortedDataByRank(){

        this.data = this.data.stream().sorted().toList();

        return this;
    }
}
