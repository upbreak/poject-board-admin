package com.fastcampus.projectboardadmin.service;

import com.fastcampus.projectboardadmin.domain.constant.RoleType;
import com.fastcampus.projectboardadmin.dto.ArticleCommentDto;
import com.fastcampus.projectboardadmin.dto.ArticleDto;
import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fastcampus.projectboardadmin.dto.properties.ProjectProperties;
import com.fastcampus.projectboardadmin.dto.reponse.ArticleClientResponse;
import com.fastcampus.projectboardadmin.dto.reponse.ArticleCommentClientResponse;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ActiveProfiles("testdb")
@DisplayName("비즈니스 로직 - 댓글 관리")
class ArticleCommentManagementServiceTest {

//    @Disabled("실제 API 호출 결과 관찰용이므로 평상시엔 비활성화")
    @DisplayName("실제 API 호출 테스트")
    @SpringBootTest
    @Nested
    class RealRestTest{
        private final ArticleCommentManagementService sut;

        public RealRestTest(@Autowired ArticleCommentManagementService sut) {
            this.sut = sut;
        }

        @DisplayName("댓글 API를 호출하면, 댓글을 가져온다.")
        @Test
        void givenNoting_whenCallingArticleCommentsApi_thenReturnArticleCommentsList() {
            // Given

            // When
            List<ArticleCommentDto> result = sut.getArticleComments();

            // Then
            System.out.println(result.stream().findFirst());
            assertThat(result).isNotNull();
        }
    }

    @DisplayName("API mocking 테스트")
    @EnableConfigurationProperties(ProjectProperties.class)
    @AutoConfigureWebClient(registerRestTemplate = true)
    @RestClientTest(ArticleCommentManagementService.class)
    @Nested
    class RestTemplateTest{

        private final ArticleCommentManagementService sut;
        private final ProjectProperties projectProperties;
        private final MockRestServiceServer server;
        private final ObjectMapper mapper;

        @Autowired
        public RestTemplateTest(ArticleCommentManagementService sut, ProjectProperties projectProperties, MockRestServiceServer server, ObjectMapper mapper) {
            this.sut = sut;
            this.projectProperties = projectProperties;
            this.server = server;
            this.mapper = mapper;
        }

        @DisplayName("댓글 API를 호출하면, 댓글을 가져온다.")
        @Test
        void givenNoting_whenCallingArticleCommentsApi_thenReturnArticleCommentsList() throws JsonProcessingException {
            // Given
            ArticleCommentDto expectedArticle = createArticleCommentDto("글");
            ArticleCommentClientResponse expectedReponse = ArticleCommentClientResponse.of(List.of(expectedArticle));
            server.expect(requestTo(projectProperties.board().url() + "/api/articleComments?size=10000"))
                    .andRespond(withSuccess(
                            mapper.writeValueAsString(expectedReponse)
                            , MediaType.APPLICATION_JSON
                    ));

            // When
            List<ArticleCommentDto> results = sut.getArticleComments();

            // Then
            assertThat(results).first()
                    .hasFieldOrPropertyWithValue("id", expectedArticle.id())
                    .hasFieldOrPropertyWithValue("articleId", expectedArticle.articleId())
                    .hasFieldOrPropertyWithValue("content", expectedArticle.content())
                    .hasFieldOrPropertyWithValue("useAccount.nickname", expectedArticle.userAccountDto().nickname());
            server.verify();
        }

        @DisplayName("댓글 목록 API를 호출하면, 댓글을 가져온다.")
        @Test
        void givenNoting_whenCallingArticleCommentsApi_thenReturnArticleComments() throws JsonProcessingException {
            // Given
            Long articleId = 1L;
            ArticleCommentDto expectedArticle = createArticleCommentDto("글");
            server.expect(requestTo(projectProperties.board().url() + "/api/articleComments/" + articleId))
                    .andRespond(withSuccess(
                            mapper.writeValueAsString(expectedArticle)
                            , MediaType.APPLICATION_JSON
                    ));

            // When
            ArticleCommentDto results = sut.getArticleComment(articleId);

            // Then
            assertThat(results)
                    .hasFieldOrPropertyWithValue("id", articleId)
                    .hasFieldOrPropertyWithValue("articleId", expectedArticle.articleId())
                    .hasFieldOrPropertyWithValue("content", expectedArticle.content())
                    .hasFieldOrPropertyWithValue("useAccount.nickname", expectedArticle.userAccountDto().nickname());
            server.verify();
        }

        @DisplayName("댓글글 목록 API를 호출하면, 댓글을 삭제한다.")
        @Test
        void givenNoting_whenCallingDeleteArticleCommentsApi_thenDeleteArticle() {
            // Given
            Long articleId = 1L;
            server.expect(requestTo(projectProperties.board().url() + "/api/articleComments/" + articleId))
                    .andExpect(method(HttpMethod.DELETE))
                    .andRespond(withSuccess());

            // When
            sut.deleteArticleComment(articleId);

            // Then
            server.verify();
        }
    }
    private ArticleCommentDto createArticleCommentDto(String content) {
        return ArticleCommentDto.of(
                1L
                , 1L
                , createUserAccountDto()
                , null
                , content
                , LocalDateTime.now()
                , "Jinwoo"
                , LocalDateTime.now()
                , "Jinwoo"
        );
    }

    private UserAccountDto createUserAccountDto() {
        return UserAccountDto.of(
                "jinwoo"
                , "pw"
                , Set.of(RoleType.ADMIN)
                , "jinwoo@email.com"
                , "jinwoo"
                , "memo"
        );
    }
}