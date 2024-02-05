package com.fastcampus.projectboardadmin.controller;

import com.fastcampus.projectboardadmin.config.SecurityConfig;
import com.fastcampus.projectboardadmin.domain.constant.RoleType;
import com.fastcampus.projectboardadmin.dto.ArticleCommentDto;
import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fastcampus.projectboardadmin.service.ArticleCommentManagementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 댓글 관리")
@Import(SecurityConfig.class)
@WebMvcTest(ArticleCommentManagementController.class)
class ArticleCommentManagementControllerTest {

    private final MockMvc mvc;

    @MockBean
    private ArticleCommentManagementService articleCommentManagementService;

    public ArticleCommentManagementControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][get] 댓글 관리 페이지 - 정상 호출")
    @Test
    void givenNoting_whenRequestArticleCommentManageView_thenReturnArticleCommentManageView() throws Exception {
        // Given
        given(articleCommentManagementService.getArticleComments()).willReturn(List.of());

        // When & Then
        mvc.perform(get("/management/article-comments"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("management/articleComments"))
                .andExpect(model().attribute("articleComments", List.of()));
        then(articleCommentManagementService).should().getArticleComments();
    }

    @DisplayName("[data][get] 댓글 1개 - 정상 호출")
    @Test
    void givenArticleCommentId_whenRequestingArticleComment_thenReturnArticleComment() throws Exception {
        // Given
        Long articleCommentId = 1L;
        ArticleCommentDto articleCommentDto = createArticleCommentDto("내용");
        given(articleCommentManagementService.getArticleComment(articleCommentId)).willReturn(articleCommentDto);

        // When & Then
        mvc.perform(get("/management/article-comments/" + articleCommentId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(articleCommentId))
                .andExpect(jsonPath("$.cotent").value(articleCommentDto.content()))
                .andExpect(jsonPath("$.userAccount.nickname").value(articleCommentDto.userAccountDto().nickname()));
        then(articleCommentManagementService).should().getArticleComment(articleCommentId);
    }

    @DisplayName("[view][post] 댓글 삭제 - 정상 호출")
    @Test
    void givenArticleCommentId_whenRequestDeleteArticleComment_thenDeleteArticleComment() throws Exception {
        // Given
        Long articleCommentId = 1L;
        willDoNothing().given(articleCommentManagementService).deleteArticleComment(articleCommentId);

        // When & Then
        mvc.perform(post("/management/article-comments/" + articleCommentId))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/management/article-comments"))
                .andExpect(redirectedUrl("/management/article-comments"));
        then(articleCommentManagementService).should().deleteArticleComment(articleCommentId);
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
                , Set.of(RoleType.ADMIN)
                , "jinwoo@email.com"
                , "jinwoo"
                , "memo"
        );
    }
}