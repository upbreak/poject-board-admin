package com.fastcampus.projectboardadmin.controller;

import com.fastcampus.projectboardadmin.dto.reponse.ArticleCommentResponse;
import com.fastcampus.projectboardadmin.service.ArticleCommentManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/management/article-comments")
@Controller
public class ArticleCommentManagementController {

    private final ArticleCommentManagementService articleCommentManagementService;

    @GetMapping
    public String articleComments(
            Model model
            , HttpServletRequest request
    ){
        List<ArticleCommentResponse> responses = articleCommentManagementService.getArticleComments().stream()
                .map(ArticleCommentResponse::from)
                .toList();

        model.addAttribute("comments", responses);
        model.addAttribute("requestURI", request.getRequestURI());

        return "management/articleComments";
    }

    @ResponseBody
    @GetMapping("{articleCommentId}")
    public ArticleCommentResponse articleComment(
            @PathVariable Long articleCommentId
    ){
        return ArticleCommentResponse.from(articleCommentManagementService.getArticleComment(articleCommentId));
    }

    @PostMapping("/{articleCommentId}")
    public String deleteArticleComment(
            @PathVariable Long articleCommentId
    ){
        articleCommentManagementService.deleteArticleComment(articleCommentId);

        return "redirect:/management/article-comments";
    }
}
