package com.fastcampus.projectboardadmin.service;

import com.fastcampus.projectboardadmin.domain.constant.RoleType;
import com.fastcampus.projectboardadmin.dto.ArticleDto;
import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fastcampus.projectboardadmin.dto.properties.ProjectProperties;
import com.fastcampus.projectboardadmin.dto.reponse.ArticleClientResponse;
import com.fastcampus.projectboardadmin.dto.reponse.UserAcccountClientResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("testdb")
@DisplayName("비즈니스 로직 - 회원 관리")
class UserAccountManagementServiceTest {
    
//    @Disabled("실제 API 호출 결과 관찰용이므로 평상시엔 비활성화")
    @DisplayName("실제 API 호출 테스트")
    @SpringBootTest
    @Nested
    class RealRestTest{
        private final UserAccountManagementService sut;

        public RealRestTest(@Autowired UserAccountManagementService sut) {
            this.sut = sut;
        }

        @DisplayName("회원관리 API를 호출하면, 회원정보 리스트를 가져온다.")
        @Test
        void givenNoting_whenCallingUserAccountApi_thenReturnUserAccountList() {
            // Given

            // When
            List<UserAccountDto> result = sut.getUserAccounts();

            // Then
            System.out.println(result.stream().findFirst());
            assertThat(result).isNotNull();
        }
    }

    @DisplayName("API mocking 테스트")
    @EnableConfigurationProperties(ProjectProperties.class)
    @AutoConfigureWebClient(registerRestTemplate = true)
    @RestClientTest(UserAccountManagementService.class)
    @Nested
    class RestTemplateTest{

        private final UserAccountManagementService sut;
        private final ProjectProperties projectProperties;
        private final MockRestServiceServer server;
        private final ObjectMapper mapper;

        @Autowired
        public RestTemplateTest(UserAccountManagementService sut, ProjectProperties projectProperties, MockRestServiceServer server, ObjectMapper mapper) {
            this.sut = sut;
            this.projectProperties = projectProperties;
            this.server = server;
            this.mapper = mapper;
        }

        @DisplayName("회원관리 목록 API를 호출하면, 회원정보들을 가져온다.")
        @Test
        void givenNoting_whenCallingUserAccountApi_thenReturnUserAccountList() throws JsonProcessingException {
            // Given
            String userId = "test";
            String nickname = "jinwoo";
            UserAccountDto expectedUserAccount = createUserAccountDto(userId, nickname);
            UserAcccountClientResponse expectedReponse = UserAcccountClientResponse.of(List.of(expectedUserAccount));
            server.expect(requestTo(projectProperties.board().url() + "/api/userAccounts?size=10000"))
                    .andRespond(withSuccess(
                            mapper.writeValueAsString(expectedReponse)
                            , MediaType.APPLICATION_JSON
                    ));

            // When
            List<UserAccountDto> results = sut.getUserAccounts();

            // Then
            assertThat(results).first()
                    .hasFieldOrPropertyWithValue("userId", userId)
                    .hasFieldOrPropertyWithValue("nickname", nickname)
                    .hasFieldOrPropertyWithValue("email", expectedUserAccount.email());
            server.verify();
        }

        @DisplayName("회원관리 API를 호출하면, 회원정보를 가져온다.")
        @Test
        void givenNoting_whenCallingUserAccountApi_thenReturnUserAccount() throws JsonProcessingException {
            // Given
            String userId = "test";
            String nickname = "jinwoo";
            UserAccountDto expectedUserAccount = createUserAccountDto(userId, nickname);
            server.expect(requestTo(projectProperties.board().url() + "/api/userAccounts/" + userId))
                    .andRespond(withSuccess(
                            mapper.writeValueAsString(expectedUserAccount)
                            , MediaType.APPLICATION_JSON
                    ));

            // When
            UserAccountDto results = sut.getUserAccount(userId);

            // Then
            assertThat(results)
                    .hasFieldOrPropertyWithValue("userId", userId)
                    .hasFieldOrPropertyWithValue("nickname", nickname)
                    .hasFieldOrPropertyWithValue("email", expectedUserAccount.email());
            server.verify();
        }

        @DisplayName("회원관리 삭제 API를 호출하면, 회원을 삭제한다.")
        @Test
        void givenNoting_whenCallingDeleteUserAccountApi_thenDeleteUserAccount() {
            // Given
            String userId = "test";
            String nickname = "jinwoo";
            server.expect(requestTo(projectProperties.board().url() + "/api/userAccounts/" + userId))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withSuccess());

            // When
            sut.deleteUserAccount(userId);

            // Then
            server.verify();
        }
    }

    private UserAccountDto createUserAccountDto(String userId, String nickname) {
        return UserAccountDto.of(
                userId
                , "jinwoo@email.com"
                , nickname
                , "memo"
        );
    }
}