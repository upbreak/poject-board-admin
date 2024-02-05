package com.fastcampus.projectboardadmin.dto.reponse;

import com.fastcampus.projectboardadmin.dto.ArticleDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ArticleClientResponse(
        @JsonProperty("_embedded")Embedded embedded
        , @JsonProperty("page")Page page
) {
    public static ArticleClientResponse empty(){
        return new ArticleClientResponse(
                new Embedded(List.of())
                , new Page(1, 0, 1, 0)
        );
    }

    public static ArticleClientResponse of(List<ArticleDto> articleDtos){
        return new ArticleClientResponse(
                new Embedded(articleDtos)
                , new Page(articleDtos.size(), articleDtos.size(), 1, 0)
        );
    }

    public List<ArticleDto> articles() {
        return this.embedded.articleDtos;
    }

    public record Embedded(@JsonProperty("articles") List<ArticleDto> articleDtos){}

    /**
     * api로 받아오는 json데이터의 page안쪽 필드명이 동일하기 때문에 @JsonProperty를 지정해주지 않음.
     *
     * @param size
     * @param totalElements
     * @param totalPages
     * @param number
     */
    public record Page(
            int size
            , long totalElements
            , int totalPages
            , int number
    ){}
}
