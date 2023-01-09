package com.newz.api.vogueNews.model.vogueNews.dto;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class VogueNewsArticleDto {
    /**
     * image, snippet, source, timeAgo, title, url
     * */

    VogueNewsArticleImageDto vogueNewsArticleImageDto = null;
    String summary;
    String source;
    String timeAgo;
    String title;
    String newsUrl;

    public VogueNewsArticleDto(String summary, String source, String timeAgo, String title, String newsUrl) {
        this.summary = summary;
        this.source = source;
        this.timeAgo = timeAgo;
        this.title = title;
        this.newsUrl = newsUrl;
    }

    public VogueNewsArticleDto(VogueNewsArticleImageDto vogueNewsArticleImageDto, String summary, String source, String timeAgo, String title, String newsUrl) {
        this.vogueNewsArticleImageDto = vogueNewsArticleImageDto;
        this.summary = summary;
        this.source = source;
        this.timeAgo = timeAgo;
        this.title = title;
        this.newsUrl = newsUrl;
    }

    public static List<VogueNewsArticleDto> fromArticleJsonObject(JsonArray articleJsonArray){
        List<VogueNewsArticleDto> vogueNewsArticleDtoList = new ArrayList<>();

        for(int index=0;index<articleJsonArray.size();index++){
            JsonElement articleJsonObjectElement = articleJsonArray.get(index);
            JsonObject articleJsonObject = articleJsonObjectElement.getAsJsonObject();

            VogueNewsArticleDto vogueNewsArticleDto;

            if(articleJsonObject.get("image") != null){
                vogueNewsArticleDto = generateInstanceWithoutImage(articleJsonObject);
            }
            else{
                vogueNewsArticleDto = generateInstanceWithImage(articleJsonObject);
            }

            vogueNewsArticleDtoList.add(vogueNewsArticleDto);
        }

        return vogueNewsArticleDtoList;
    }

    private static VogueNewsArticleDto generateInstanceWithImage(JsonObject articleJsonObject){
        return new VogueNewsArticleDto(
            String.valueOf(articleJsonObject.get("snippet")),
            String.valueOf(articleJsonObject.get("source")),
            String.valueOf(articleJsonObject.get("timeAgo")),
            String.valueOf(articleJsonObject.get("title")),
            String.valueOf(articleJsonObject.get("url"))
        );
    }

    private static VogueNewsArticleDto generateInstanceWithoutImage(JsonObject articleJsonObject){

        return new VogueNewsArticleDto(
                VogueNewsArticleImageDto.fromArticleImageJsonObject(articleJsonObject),
                String.valueOf(articleJsonObject.get("snippet")),
                String.valueOf(articleJsonObject.get("source")),
                String.valueOf(articleJsonObject.get("timeAgo")),
                String.valueOf(articleJsonObject.get("title")),
                String.valueOf(articleJsonObject.get("url"))
        );
    }
}
