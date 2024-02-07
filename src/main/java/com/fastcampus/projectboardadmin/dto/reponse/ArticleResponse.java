package com.fastcampus.projectboardadmin.dto.reponse;

import com.fastcampus.projectboardadmin.dto.ArticleDto;
import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArticleResponse(
        Long id
        , UserAccountDto userAccount
        , String title
        , String content
        , LocalDateTime createAt
) {

    public static ArticleResponse of(Long id, UserAccountDto userAccount, String title, String content, LocalDateTime createAt) {
        return new ArticleResponse(id, userAccount, title, content, createAt);
    }

    public static ArticleResponse withContent(ArticleDto dto){
        return ArticleResponse.of(
                dto.id()
                , dto.userAccount()
                , dto.title()
                , dto.content()
                , dto.createAt()
        );
    }

    public static ArticleResponse withoutContent(ArticleDto dto){
        return ArticleResponse.of(
                dto.id()
                , dto.userAccount()
                , dto.title()
                , null
                , dto.createAt()
        );
    }
}
