package com.fastcampus.projectboardadmin.controller;

import com.fastcampus.projectboardadmin.config.TestSecurityConfig;
import com.fastcampus.projectboardadmin.dto.UserAccountDto;
import com.fastcampus.projectboardadmin.service.UserAccountManagementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View 컨트롤러 - 회원 관리")
@Import(TestSecurityConfig.class)
@WebMvcTest(UserAccountManagementController.class)
class UserAccountManagementControllerTest {

    private final MockMvc mvc;

    @MockBean
    private UserAccountManagementService userAccountManagementService;

    public UserAccountManagementControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][get] 회원 관리 페이지 - 정상 호출")
    @WithMockUser(username = "tester", roles = "USER")
    @Test
    void givenNothing_whenRequestingUserAccountManagementView_thenReturnsUserAccountManagementView() throws Exception {
        // Given
        given(userAccountManagementService.getUserAccounts()).willReturn(List.of());

        // When & Then
        mvc.perform(get("/management/user-accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("management/userAccounts"))
                .andExpect(model().attribute("accounts", List.of()));
        then(userAccountManagementService).should().getUserAccounts();
    }

    @DisplayName("[data][get] 회원 정보 1개 - 정상 호출")
    @WithMockUser(username = "tester", roles = "USER")
    @Test
    void givenUserAccount_whenRequestUserAccount_thenReturnUserAccount() throws Exception {
        // Given
        String userId = "test";
        String nickname = "jinwoo";
        UserAccountDto userAccountDto = createUserAccountDto(userId, nickname);
        given(userAccountManagementService.getUserAccount(userId)).willReturn(userAccountDto);

        // When & Then
        mvc.perform(get("/management/user-accounts/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userAccountDto.userId()))
                .andExpect(jsonPath("$.nickname").value(userAccountDto.nickname()))
                .andExpect(jsonPath("$.email").value(userAccountDto.email()));
        then(userAccountManagementService).should().getUserAccount(userId);
    }

    @DisplayName("[view][post] 회원 정보 삭제 - 정상 호출")
    @WithMockUser(username = "tester", roles = "MANAGER")
    @Test
    void givenArticleId_whenRequestingDeleteArticle_thenRedirectArticleManageView() throws Exception {
        // Given
        String userId = "test";
        willDoNothing().given(userAccountManagementService).deleteUserAccount(userId);

        // When & Then
        mvc.perform(post("/management/user-accounts/" + userId)
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/management/user-accounts"))
                .andExpect(redirectedUrl("/management/user-accounts"));
        then(userAccountManagementService).should().deleteUserAccount(userId);
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