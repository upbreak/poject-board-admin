package com.fastcampus.projectboardadmin.dto.reponse;

import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserAcccountClientResponse(
        @JsonProperty("_embedded") UserAcccountClientResponse.Embedded embedded
        , @JsonProperty("page") UserAcccountClientResponse.Page page
) {
    public static UserAcccountClientResponse empty(){
        return new UserAcccountClientResponse(
                new UserAcccountClientResponse.Embedded(List.of())
                , new UserAcccountClientResponse.Page(1, 0, 1, 0)
        );
    }

    public static UserAcccountClientResponse of(List<UserAccountDto> UserAccountDtos){
        return new UserAcccountClientResponse(
                new UserAcccountClientResponse.Embedded(UserAccountDtos)
                , new UserAcccountClientResponse.Page(UserAccountDtos.size(), UserAccountDtos.size(), 1, 0)
        );
    }

    public List<UserAccountDto> articles() {
        return this.embedded.UserAccountDtos;
    }

    public record Embedded(@JsonProperty("userAccounts") List<UserAccountDto> UserAccountDtos){}

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
