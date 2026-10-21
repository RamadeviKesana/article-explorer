package com.example.articles.controller;

import com.example.articles.dto.ArticleDto;
import com.example.articles.dto.CreateArticleRequest;
import com.example.articles.service.ArticleService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/tags")
    public List<String> getTags() {
        return articleService.allTags();
    }

    @GetMapping("/articles")
    public Page<ArticleDto> getArticles(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(name = "tag", required = false) List<String> tags,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {

        return articleService.search(keyword, tags, page, size);
    }

    @PostMapping("/articles")
    @ResponseStatus(HttpStatus.CREATED)
    public ArticleDto createArticle(@RequestBody CreateArticleRequest request) {
        return articleService.create(request);
    }
}
