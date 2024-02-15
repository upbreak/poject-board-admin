package com.fastcampus.projectboardadmin.dto.reponse;

import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UserAccountClientResponse(
        @JsonProperty("_embedded") UserAccountClientResponse.Embedded embedded
        , @JsonProperty("page") UserAccountClientResponse.Page page
) {
    public static UserAccountClientResponse empty(){
        return new UserAccountClientResponse(
                new UserAccountClientResponse.Embedded(List.of())
                , new UserAccountClientResponse.Page(1, 0, 1, 0)
        );
    }

    public static UserAccountClientResponse of(List<UserAccountDto> UserAccountDtos){
        return new UserAccountClientResponse(
                new UserAccountClientResponse.Embedded(UserAccountDtos)
                , new UserAccountClientResponse.Page(UserAccountDtos.size(), UserAccountDtos.size(), 1, 0)
        );
    }

    public List<UserAccountDto> userAccounts() {
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
