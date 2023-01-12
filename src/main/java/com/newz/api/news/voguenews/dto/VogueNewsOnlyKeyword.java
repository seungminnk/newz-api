package com.newz.api.news.voguenews.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VogueNewsOnlyKeyword {

    public VogueNewsOnlyKeyword(String keyword) {
        this.keyword = keyword;
    }

    String keyword;

    public VogueNewsOnlyKeyword unContaminatedKeyword(){

        String tmpKeyword = this.getKeyword();
        String unContaminatedKeyword = tmpKeyword.replaceAll("\"", "");

        this.setKeyword(unContaminatedKeyword);
        return this;
    }
}
