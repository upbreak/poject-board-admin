package com.fastcampus.projectboardadmin.dto.reponse;

import com.fastcampus.projectboardadmin.dto.ArticleCommentDto;
import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ArticleCommentResponse(
        Long id
        , UserAccountDto userAccount
        , String content
        , LocalDateTime createAt
) {
    public static ArticleCommentResponse of(Long id, UserAccountDto userAccount, String content, LocalDateTime createAt) {
        return new ArticleCommentResponse(id, userAccount, content, createAt);
    }

    public static ArticleCommentResponse from(ArticleCommentDto dto){
        return ArticleCommentResponse.of(
                dto.id()
                , dto.userAccount()
                , dto.content()
                , dto.createAt()
        );
    }
}
