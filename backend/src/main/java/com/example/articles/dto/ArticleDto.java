package com.example.articles.dto;

import java.util.List;

public record ArticleDto(Long id, String title, String summary, List<String> tags) {
}
