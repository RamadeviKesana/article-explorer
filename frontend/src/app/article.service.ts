import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Article {
  id: number;
  title: string;
  summary: string;
  tags: string[];
}

export interface ArticlePage {
  content: Article[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

@Injectable({ providedIn: 'root' })
export class ArticleService {
  private readonly apiUrl = 'http://localhost:8080/api';

  constructor(private readonly http: HttpClient) {}

  getTags(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/tags`);
  }

  search(keyword: string, tags: string[], page: number, size = 12): Observable<ArticlePage> {
    let params = new HttpParams()
      .set('keyword', keyword)
      .set('page', page)
      .set('size', size);

    for (const tag of tags) {
      params = params.append('tag', tag);
    }

    return this.http.get<ArticlePage>(`${this.apiUrl}/articles`, { params });
  }
}
