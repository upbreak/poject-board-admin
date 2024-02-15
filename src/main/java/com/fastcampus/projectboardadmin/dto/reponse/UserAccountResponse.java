package com.fastcampus.projectboardadmin.dto.reponse;

import com.fastcampus.projectboardadmin.dto.UserAccountDto;

import java.time.LocalDateTime;

public record UserAccountResponse(
        String userId
        , String email
        , String nickname
        , String memo
        , String createBy
        , LocalDateTime createAt
) {
    public static UserAccountResponse of(String userId, String email, String nickname, String memo, String createBy, LocalDateTime createAt) {
        return new UserAccountResponse(userId, email, nickname, memo, createBy, createAt);
    }

    public static UserAccountResponse from(UserAccountDto dto){
        return UserAccountResponse.of(
                dto.userId()
                , dto.email()
                , dto.nickname()
                , dto.memo()
                , dto.createBy()
                , dto.createAt()
        );
    }
}
