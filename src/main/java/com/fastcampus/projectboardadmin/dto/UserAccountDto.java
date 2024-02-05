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
        , Set<RoleType> roleTypes
        , String email
        , String nickname
        , String memo
        , LocalDateTime createAt
        , String createBy
        , LocalDateTime modifiedAt
        , String modifiedBy
) {
    public static UserAccountDto of(String userId, Set<RoleType> roleTypes,  String email, String nickname, String memo) {
        return UserAccountDto.of(userId, roleTypes, email, nickname, memo, null, null);
    }

    public static UserAccountDto of(String userId, Set<RoleType> roleTypes, String email, String nickname, String memo, String createBy, String modifiedBy) {
        return new UserAccountDto(userId, roleTypes, email, nickname, memo, null, createBy, null, modifiedBy);
    }

}
