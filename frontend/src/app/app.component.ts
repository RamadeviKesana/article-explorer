import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Article, ArticleService } from './article.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './app.component.html',
})
export class AppComponent implements OnInit {
  tags: string[] = [];
  selectedTags: string[] = [];
  articles: Article[] = [];
  keyword = '';
  page = 0;
  totalPages = 0;
  totalElements = 0;
  loading = false;
  error = '';

  constructor(private readonly articleService: ArticleService) {}

  ngOnInit(): void {
    this.articleService.getTags().subscribe({
      next: (tags) => (this.tags = tags),
      error: () => (this.error = 'Unable to load tags. Is Spring Boot running?'),
    });
    this.loadArticles();
  }

  toggleTag(tag: string): void {
    this.selectedTags = this.selectedTags.includes(tag)
      ? this.selectedTags.filter((selected) => selected !== tag)
      : [...this.selectedTags, tag];

    this.page = 0;
    this.loadArticles();
  }

  onKeywordChange(): void {
    this.page = 0;
    this.loadArticles();
  }

  clear(): void {
    this.keyword = '';
    this.selectedTags = [];
    this.page = 0;
    this.loadArticles();
  }

  movePage(delta: number): void {
    const nextPage = this.page + delta;
    if (nextPage >= 0 && nextPage < this.totalPages) {
      this.page = nextPage;
      this.loadArticles();
    }
  }

  loadArticles(): void {
    this.loading = true;
    this.error = '';

    this.articleService.search(this.keyword, this.selectedTags, this.page).subscribe({
      next: (response) => {
        this.articles = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
        this.loading = false;
      },
      error: () => {
        this.error = 'Unable to load articles. Check the API at localhost:8080.';
        this.loading = false;
      },
    });
  }
}
