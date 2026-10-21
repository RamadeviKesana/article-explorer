# Article Explorer — maintainable interview practice

Java 21 / Spring Boot 3.5 / Spring Data JPA / H2 / Angular 18.

## Changes from the original demo

- Replaced synthetic numbered articles with 12 curated, topic-specific development examples.
- Reformatted Java, TypeScript, and Angular HTML for readability.
- Added POST `/api/articles` with field checks and existing-tag validation.
- Preserved GET `/api/articles`, GET `/api/tags`, pagination, keyword search, and OR tag filtering.
- Seed sample articles only when the `dev` profile is active and both tables are empty.

## Running

1. Open `backend/pom.xml` in IntelliJ with JDK 21 and run `ArticleFilterApplication`.
2. In the `frontend` folder, run `npm install` then `npm start`.
3. Open `http://localhost:4200` and the H2 console at `http://localhost:8080/h2-console`.
4. H2 JDBC URL: `jdbc:h2:mem:articlecatalog`, username `sa`, empty password.

## Create an article using Bruno/Postman

POST `http://localhost:8080/api/articles` with JSON body:

```json
{
  "title": "Understanding Caching in Spring Boot",
  "summary": "Compare cache-aside, eviction, TTL, and consistency trade-offs.",
  "tags": ["Java", "Spring Boot", "Database"]
}
```

Expect HTTP 201 Created and JSON with the new article ID. GET `/api/articles?tag=Java` will include the new record.

**Note:** This is still an interview-development project, not a deploy-ready production system. Add authentication/authorization before exposing POST publicly; use database migrations (e.g. Flyway), persistent storage, tests, and more robust error/observability handling for production. The frontend still uses a simple subscription pattern; rapid filter changes could return out of order. In a larger app, debounce keyword search and use `switchMap` for cancellation. The query uses OR semantics (`AI` OR `Java`), not AND.

**Data lifecycle:** The H2 database is in-memory and `ddl-auto=create-drop` is intended for demo use; new articles disappear after the backend stops. Persistent content needs a file-backed database or PostgreSQL plus migrations.
