package com.newz.api.vogueNews.model.vogueNews.dto;

import com.google.gson.JsonObject;
import lombok.Getter;

@Getter
public class VogueNewsArticleImageDto {
    String imageUrl;
    String imageSource;

    public VogueNewsArticleImageDto(String imageUrl, String imageSource) {
        this.imageUrl = imageUrl;
        this.imageSource = imageSource;
    }

    public static VogueNewsArticleImageDto fromArticleImageJsonObject(JsonObject articleImageJsonObject){
        return new VogueNewsArticleImageDto(
            String.valueOf(articleImageJsonObject.get("image").getAsJsonObject().get("imageUrl")),
            String.valueOf(articleImageJsonObject.get("image").getAsJsonObject().get("source"))
        );
    }
}
