package com.example.articles.service;

import com.example.articles.dto.ArticleDto;
import com.example.articles.dto.CreateArticleRequest;
import com.example.articles.model.Article;
import com.example.articles.model.Tag;
import com.example.articles.repository.ArticleRepository;
import com.example.articles.repository.TagRepository;
import jakarta.persistence.criteria.Join;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    public ArticleService(ArticleRepository articleRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> search(String keyword, List<String> selectedTags, int page, int size) {
        if (page < 0 || size < 1 || size > 100) {
            throw new ResponseStatusException(BAD_REQUEST, "Page must be >= 0 and size must be 1–100");
        }

        Specification<Article> criteria = (root, query, builder) -> builder.conjunction();

        if (keyword != null && !keyword.isBlank()) {
            String text = "%" + keyword.trim().toLowerCase(Locale.ROOT) + "%";
            criteria = criteria.and((root, query, builder) -> builder.or(
                    builder.like(builder.lower(root.get("title")), text),
                    builder.like(builder.lower(root.get("summary")), text)
            ));
        }

        List<String> normalizedTags = selectedTags == null ? List.of() : selectedTags.stream()
                .filter(tag -> tag != null && !tag.isBlank())
                .map(tag -> tag.trim().toLowerCase(Locale.ROOT))
                .distinct()
                .toList();

        if (!normalizedTags.isEmpty()) {
            // OR semantics: an article can match ANY selected tag.
            criteria = criteria.and((root, query, builder) -> {
                query.distinct(true);
                Join<Article, Tag> tagJoin = root.join("tags");
                return builder.lower(tagJoin.get("name")).in(normalizedTags);
            });
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return articleRepository.findAll(criteria, pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<String> allTags() {
        return tagRepository.findAllByOrderByNameAsc().stream().map(Tag::getName).toList();
    }

    @Transactional
    public ArticleDto create(CreateArticleRequest request) {
        if (request == null || request.title() == null || request.title().isBlank()
                || request.title().trim().length() > 180) {
            throw new ResponseStatusException(BAD_REQUEST, "Title is required (max 180 characters)");
        }
        if (request.summary() == null || request.summary().isBlank()
                || request.summary().trim().length() > 2000) {
            throw new ResponseStatusException(BAD_REQUEST, "Summary is required (max 2000 characters)");
        }
        if (request.tags() == null || request.tags().isEmpty() || request.tags().size() > 10) {
            throw new ResponseStatusException(BAD_REQUEST, "Select 1 to 10 tags");
        }

        Set<String> requestedNames = new LinkedHashSet<>();
        for (String name : request.tags()) {
            if (name == null || name.isBlank()) {
                throw new ResponseStatusException(BAD_REQUEST, "Tag names cannot be blank");
            }
            requestedNames.add(name.trim().toLowerCase(Locale.ROOT));
        }

        Set<Tag> resolvedTags = new LinkedHashSet<>();
        for (Tag tag : tagRepository.findAll()) {
            if (requestedNames.contains(tag.getName().toLowerCase(Locale.ROOT))) {
                resolvedTags.add(tag);
            }
        }
        if (resolvedTags.size() != requestedNames.size()) {
            throw new ResponseStatusException(BAD_REQUEST, "One or more tags do not exist");
        }

        Article saved = articleRepository.save(new Article(
                request.title().trim(), request.summary().trim(), resolvedTags
        ));
        return toDto(saved);
    }

    private ArticleDto toDto(Article article) {
        List<String> tags = article.getTags().stream().map(Tag::getName).sorted().toList();
        return new ArticleDto(article.getId(), article.getTitle(), article.getSummary(), tags);
    }
}
