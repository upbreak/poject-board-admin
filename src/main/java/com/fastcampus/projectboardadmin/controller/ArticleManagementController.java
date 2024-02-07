package com.fastcampus.projectboardadmin.controller;

import com.fastcampus.projectboardadmin.dto.reponse.ArticleResponse;
import com.fastcampus.projectboardadmin.service.ArticleManagementService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/management/articles")
@Controller
public class ArticleManagementController {

    private final ArticleManagementService articleManagementService;

    @GetMapping
    public String articles(
            Model model
            , HttpServletRequest request
            ){
        List<ArticleResponse> articles = articleManagementService.getArticles().stream()
                .map(ArticleResponse::withoutContent)
                .toList();
        model.addAttribute("articles", articles);
        model.addAttribute("requestURI", request.getRequestURI());

        return "management/articles";
    }

    @ResponseBody
    @GetMapping("/{id}")
    public ArticleResponse article(
            @PathVariable Long id
    ){
        return ArticleResponse.withContent(articleManagementService.getArticle(id));
    }

    @PostMapping("/{id}")
    public String deleteArticle(
            @PathVariable Long id
    ){
        articleManagementService.deleteArticle(id);

        return "redirect:/management/articles";
    }
}
