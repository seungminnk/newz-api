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
    String title;
    String summary = null;
    String source;
    String imageUrl = null;
    String newsUrl = null;

    public VogueNewsDataDto(Integer rank, String title, String summary,String source, String imageUrl, String newsUrl) {
        this.rank = rank;
        this.title = title;
        this.summary = summary;
        this.source = source;
        this.imageUrl = imageUrl;
        this.newsUrl = newsUrl;
    }

    public static List<VogueNewsDataDto> fromJsonObject(JsonObject googleTrendsJsonObject){
        List<VogueNewsDataDto> vogueNewsDataDtoList = new ArrayList<>();
        JsonArray googleSearchTrends = googleTrendsJsonObject.get("default").getAsJsonObject().get("trendingSearchesDays").getAsJsonArray().get(0).getAsJsonObject().get("trendingSearches").getAsJsonArray();

        for(int index = 0; index < googleSearchTrends.size(); index++){

            JsonElement googleSearchTrend = googleSearchTrends.get(index);
            JsonArray googleSearchTrendArticleArray = googleSearchTrend.getAsJsonObject().get("articles").getAsJsonArray();

            String imageYN = googleSearchTrendArticleArray.get(0).getAsJsonObject().get("image") == null ? "N":"Y";
            String imageUrl = "";

            if(imageYN.equals("Y")){
                imageUrl = String.valueOf(googleSearchTrendArticleArray.get(0).getAsJsonObject().get("image").getAsJsonObject().get("imageUrl"));
            }

            vogueNewsDataDtoList.add(
                new VogueNewsDataDto(
                    index + 1,
                    String.valueOf(googleSearchTrend.getAsJsonObject().get("title").getAsJsonObject().get("query")),
                    String.valueOf(googleSearchTrendArticleArray.get(0).getAsJsonObject().get("snippet")),
                    String.valueOf(googleSearchTrendArticleArray.get(0).getAsJsonObject().get("source")),
                    imageUrl,
                    String.valueOf(googleSearchTrendArticleArray.get(0).getAsJsonObject().get("url"))
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
