package com.fastcampus.projectboardadmin.dto;

import com.fastcampus.projectboardadmin.domain.AdminAccount;
import com.fastcampus.projectboardadmin.domain.constant.RoleType;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link AdminAccount}
 */
public record UserAccountDto(
        String userId
        , String email
        , String nickname
        , String memo
        , LocalDateTime createAt
        , String createBy
        , LocalDateTime modifiedAt
        , String modifiedBy
) {
    public static UserAccountDto of(String userId,  String email, String nickname, String memo) {
        return UserAccountDto.of(userId, email, nickname, memo, null, null);
    }

    public static UserAccountDto of(String userId, String email, String nickname, String memo, String createBy, String modifiedBy) {
        return new UserAccountDto(userId, email, nickname, memo, null, createBy, null, modifiedBy);
    }

}
