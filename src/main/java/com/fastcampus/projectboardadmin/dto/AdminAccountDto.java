package com.fastcampus.projectboardadmin.dto;

import com.fastcampus.projectboardadmin.domain.AdminAccount;
import com.fastcampus.projectboardadmin.domain.constant.RoleType;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link AdminAccount}
 */
public record AdminAccountDto(
        String userId
        , String userPassword
        , Set<RoleType> roleTypes
        , String email
        , String nickname
        , String memo
        , LocalDateTime createAt
        , String createBy
        , LocalDateTime modifiedAt
        , String modifiedBy
) {
    public static AdminAccountDto of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo) {
        return AdminAccountDto.of(userId, userPassword, roleTypes, email, nickname, memo, null, null);
    }

    public static AdminAccountDto of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, String createBy, String modifiedBy) {
        return new AdminAccountDto(userId, userPassword, roleTypes, email, nickname, memo, null, createBy, null, modifiedBy);
    }

    public static AdminAccountDto from(AdminAccount entity){
        return AdminAccountDto.of(
                entity.getUserId()
                , entity.getUserPassword()
                , entity.getRoleTypes()
                , entity.getEmail()
                , entity.getNickname()
                , entity.getMemo()
                , entity.getCreateBy()
                , entity.getModifiedBy()
        );
    }

    public AdminAccount toEntity(){
        return AdminAccount.of(
                userId
                , userPassword
                , roleTypes
                , email
                , nickname
                , memo
        );
    }
}