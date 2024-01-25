package com.fastcampus.projectboardadmin.dto;

import com.fastcampus.projectboardadmin.domain.UserAccount;
import com.fastcampus.projectboardadmin.domain.constant.RoleType;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.fastcampus.projectboardadmin.domain.UserAccount}
 */
public record UserAccountDto(
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
    public static UserAccountDto of(String userId, String userPassword, Set<RoleType> roleTypes,  String email, String nickname, String memo) {
        return UserAccountDto.of(userId, userPassword, roleTypes, email, nickname, memo, null, null);
    }

    public static UserAccountDto of(String userId, String userPassword, Set<RoleType> roleTypes, String email, String nickname, String memo, String createBy, String modifiedBy) {
        return new UserAccountDto(userId, userPassword, roleTypes, email, nickname, memo, null, createBy, null, modifiedBy);
    }

    public static UserAccountDto from(UserAccount entity){
        return UserAccountDto.of(
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

    public UserAccount toEntity(){
        return UserAccount.of(
                userId
                , userPassword
                , roleTypes
                , email
                , nickname
                , memo
        );
    }
}