package com.fastcampus.projectboardadmin.dto.reponse;

import com.fastcampus.projectboardadmin.dto.ArticleCommentDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ArticleCommentClientResponse(
        @JsonProperty("_embedded") ArticleCommentClientResponse.Embedded embedded
        , @JsonProperty("page") ArticleCommentClientResponse.Page page
) {
    public static ArticleCommentClientResponse empty(){
        return new ArticleCommentClientResponse(
                new ArticleCommentClientResponse.Embedded(List.of())
                , new ArticleCommentClientResponse.Page(1, 0, 1, 0)
        );
    }

    public static ArticleCommentClientResponse of(List<ArticleCommentDto> articleCommentDtos){
        return new ArticleCommentClientResponse(
                new ArticleCommentClientResponse.Embedded(articleCommentDtos)
                , new ArticleCommentClientResponse.Page(articleCommentDtos.size(), articleCommentDtos.size(), 1, 0)
        );
    }

    public List<ArticleCommentDto> articleComments() {
        return this.embedded.articleCommentDtos;
    }

    public record Embedded(@JsonProperty("articleComments") List<ArticleCommentDto> articleCommentDtos){}

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
