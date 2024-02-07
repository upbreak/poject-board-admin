package com.fastcampus.projectboardadmin.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record ArticleDto(
        Long id
        , UserAccountDto userAccount
        , String title
        , String content
        , Set<String> hashtags
        , LocalDateTime createAt
        , String createBy
        , LocalDateTime modifiedAt
        , String modifiedBy
) {
    public static ArticleDto of(Long id, UserAccountDto userAccountDto, String title, String content, Set<String> hashtags, LocalDateTime createAt, String createBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new ArticleDto(id, userAccountDto, title, content, hashtags, createAt, createBy, modifiedAt, modifiedBy);
    }
}
