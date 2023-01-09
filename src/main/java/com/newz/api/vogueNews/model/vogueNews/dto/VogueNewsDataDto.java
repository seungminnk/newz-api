package com.newz.api.vogueNews.model.vogueNews.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VogueNewsDataDto implements Comparable<VogueNewsDataDto> {
    Integer rank;
    String keyword;
    List<VogueNewsArticleDto> vogueNewsArticleDto;

    public VogueNewsDataDto(Integer rank, String keyword, List<VogueNewsArticleDto> vogueNewsArticleDto) {
        this.rank = rank;
        this.keyword = keyword;
        this.vogueNewsArticleDto = vogueNewsArticleDto;
    }

    public static List<VogueNewsDataDto> fromJsonObject(JsonObject googleTrendsJsonObject){
        List<VogueNewsDataDto> vogueNewsDataDtoList = new ArrayList<>();
        JsonArray googleSearchTrends = googleTrendsJsonObject.get("default").getAsJsonObject().get("trendingSearchesDays").getAsJsonArray().get(0).getAsJsonObject().get("trendingSearches").getAsJsonArray();

        for(int index = 0; index < googleSearchTrends.size(); index++){

            JsonElement googleSearchTrend = googleSearchTrends.get(index);
            JsonArray googleSearchTrendArticleArray = googleSearchTrend.getAsJsonObject().get("articles").getAsJsonArray();

            int rank = index + 1;
            String keyword = String.valueOf(googleSearchTrend.getAsJsonObject().get("title").getAsJsonObject().get("query"));

            vogueNewsDataDtoList.add(
                new VogueNewsDataDto(
                    rank,
                    keyword,
                    VogueNewsArticleDto.fromArticleJsonObject(googleSearchTrendArticleArray)
                )
            );
        }

        return vogueNewsDataDtoList;
    }

    @Override
    public int compareTo(VogueNewsDataDto o) {
        return this.rank.compareTo(o.rank);
    }
}
