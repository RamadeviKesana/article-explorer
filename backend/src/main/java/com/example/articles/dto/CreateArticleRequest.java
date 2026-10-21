package com.example.articles.dto;

import java.util.List;

public record CreateArticleRequest(String title, String summary, List<String> tags) {
}
