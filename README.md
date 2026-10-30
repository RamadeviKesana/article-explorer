Article Explorer
A full-stack technical article discovery application using Java, Spring Boot, Angular, TypeScript, Spring Data JPA, and H2. It demonstrates how a frontend retrieves article data from REST APIs, how articles are persisted, and how multiple tags can be associated with an article.
Project handbook: This README is intended to remain a living reference for local setup, database inspection, architecture, troubleshooting, and changes made over time.

Contents
1. Project overview
2. Technology stack
3. Architecture and data flow
4. Project layout
5. Running locally
6. H2 database console
7. H2 SQL query reference
8. Development sample data
9. Troubleshooting
10. Development notes and roadmap
Project overview
Article Explorer is a learning-oriented full-stack application for browsing technical articles and exploring topic/tag-based discovery. Article and tag data is stored in H2 during development. The backend uses Spring Data JPA repositories, while Angular is responsible for presenting article information to users.

Current areas of practice:
- Java and Spring Boot application structure
- RESTful communication between Angular and Spring Boot
- Persistence with Spring Data JPA and Hibernate
- Article–tag relationships
- Development-only sample data initialization
- SQL queries, filtering, searching, and database inspection
Technology stack
Layer	Technology	Role
Frontend	Angular, TypeScript	Article browsing and user interaction
Backend	Java, Spring Boot 3.5.6	Application startup and REST backend
Data access	Spring Data JPA, Hibernate	Repository operations and entity persistence
Database	H2	Lightweight development database
Build tooling	Maven, npm	Backend and frontend dependencies/builds
IDE and version control	IntelliJ IDEA, Git, GitHub	Local development and source history


Java 26 was used in a recent local run. Check backend/pom.xml for the project's configured Java requirement before selecting a JDK.
Architecture and data flow
Angular UI
   |
   | HTTP requests
   v
Spring Boot REST controllers
   |
   v
Application data access / repositories
   |
   v
Spring Data JPA + Hibernate
   |
   v
H2 database
   |-- ARTICLES
   |-- TAGS
   `-- ARTICLE_TAGS
The development data initializer (SampleData.java) seeds tags and articles on application startup only when the dev profile is active and its repository emptiness check passes. It does not send data directly to Angular; the frontend reads application data through backend endpoints.
Database relationship
An article may have several tags, and a tag can belong to several articles. The observed database schema contains:
- ARTICLES: article records
- TAGS: tag records
- ARTICLE_TAGS: links between articles and tags
The actual column names and relationship mapping are defined by the JPA entity classes; confirm them in the H2 console when changing the schema.
Project layout
article-filter-practice/
├── README.md
├── .gitignore
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/example/articles/
│       │   ├── ArticleFilterApplication.java
│       │   ├── config/SampleData.java
│       │   ├── model/
│       │   └── repository/
│       └── resources/
└── frontend/
    ├── package.json
    └── src/
This is a simplified layout of the known project structure, not an exhaustive source-file inventory.
Running locally
Prerequisites
- A JDK compatible with backend/pom.xml
- Maven (or the project's Maven wrapper, if present)
- Node.js and npm compatible with frontend/package.json
- Git (for version control)
1. Start the backend
From the project root in PowerShell:
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
Alternatively, run com.example.articles.ArticleFilterApplication from IntelliJ with the dev profile active. Confirm the console contains lines similar to:
The following 1 profile is active: "dev"
Tomcat started on port 8080 (http)
Started ArticleFilterApplication
The backend URL in the observed development configuration is http://localhost:8080.
2. Start the frontend
Open a second terminal at the project root:
cd frontend
npm install
npm start
If npm start is not defined, check frontend/package.json for the available scripts and run the correct Angular development command (often npx ng serve). Follow the terminal output for the actual frontend URL; Angular commonly uses http://localhost:4200.
The exact REST endpoint paths are not documented here because they need to be verified against your controller classes. Add an API reference once those paths are confirmed.

H2 database console
Keep the backend running before opening the H2 console.

Browser URL: http://localhost:8080/h2-console
Alternative URL: http://localhost:8080/h2-console/
H2 login field	Local development value
Driver Class	org.h2.Driver
JDBC URL	jdbc:h2:mem:articlecatalog
User Name	sa
Password	Blank, unless overridden in the application configuration


The database URL was verified from local startup logs:
H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:articlecatalog'
Important: jdbc:h2:mem:articlecatalog is an in-memory database. Its data normally disappears when the Spring Boot process stops. Restarting may therefore give you a fresh database and rerun the development seeder. Never use this setup to store important persistent data.
H2 SQL query reference
The table names verified in the H2 console are ARTICLES, TAGS, and ARTICLE_TAGS (plural). Do not use ARTICLE or TAG when querying this schema.

Execute the following statements in the H2 SQL editor, individually or as appropriate.
Inspect tables
1. List application tables
SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_SCHEMA = 'PUBLIC'
ORDER BY TABLE_NAME;
2. View all articles
SELECT * FROM ARTICLES;
3. Count articles
SELECT COUNT(*) AS TOTAL_ARTICLES
FROM ARTICLES;
4. View all tags
SELECT * FROM TAGS;
5. Count tags
SELECT COUNT(*) AS TOTAL_TAGS
FROM TAGS;
6. Inspect article-to-tag links
SELECT * FROM ARTICLE_TAGS;
7. Count article-to-tag links
SELECT COUNT(*) AS TOTAL_RELATIONSHIPS
FROM ARTICLE_TAGS;
Find and filter articles
8. Find an article by ID
SELECT * FROM ARTICLES WHERE ID = 5;
9. Search titles containing “spring” (case-insensitive)
SELECT *
FROM ARTICLES
WHERE LOWER(TITLE) LIKE '%spring%';
10. Search titles containing “java” (case-insensitive)
SELECT *
FROM ARTICLES
WHERE LOWER(TITLE) LIKE '%java%';
11. Show 10 articles with the largest IDs
SELECT *
FROM ARTICLES
ORDER BY ID DESC
LIMIT 10;
“Largest IDs” does not necessarily mean newest by creation timestamp; an ID is not a reliable audit timestamp.

12. Verify whether the current article count equals 100
SELECT CASE
    WHEN COUNT(*) = 100 THEN 'SUCCESS - 100 ARTICLES'
    ELSE 'ARTICLE COUNT IS NOT 100'
END AS STATUS
FROM ARTICLES;
Explore article–tag relationships
The following joins use the observed link-table column names ARTICLE_ID and TAG_ID. If your current schema has changed, first inspect ARTICLE_TAGS and adjust accordingly.

13. Show each article with its tags
SELECT a.ID AS ARTICLE_ID,
       a.TITLE,
       t.NAME AS TAG_NAME
FROM ARTICLES a
JOIN ARTICLE_TAGS atg ON atg.ARTICLE_ID = a.ID
JOIN TAGS t ON t.ID = atg.TAG_ID
ORDER BY a.ID, t.NAME;
14. Count articles associated with each tag
SELECT t.NAME AS TAG_NAME,
       COUNT(atg.ARTICLE_ID) AS ARTICLE_COUNT
FROM TAGS t
LEFT JOIN ARTICLE_TAGS atg ON atg.TAG_ID = t.ID
GROUP BY t.ID, t.NAME
ORDER BY ARTICLE_COUNT DESC, t.NAME;
15. Find articles associated with the Java tag
SELECT DISTINCT a.ID, a.TITLE
FROM ARTICLES a
JOIN ARTICLE_TAGS atg ON atg.ARTICLE_ID = a.ID
JOIN TAGS t ON t.ID = atg.TAG_ID
WHERE t.NAME = 'Java'
ORDER BY a.TITLE;
16. Inspect article table column names
SELECT COLUMN_NAME, DATA_TYPE, IS_NULLABLE
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_SCHEMA = 'PUBLIC'
  AND TABLE_NAME = 'ARTICLES'
ORDER BY ORDINAL_POSITION;
SQL concepts used
SQL syntax	Meaning
SELECT	Choose columns to return
FROM	Choose the source table
WHERE	Filter rows
LIKE	Match text patterns (% means any sequence)
LOWER()	Convert text to lowercase for comparison
COUNT(*)	Count matching rows
ORDER BY / DESC	Sort results / descending order
LIMIT	Limit returned rows
JOIN	Combine matching rows from tables
LEFT JOIN	Retain left-side rows even without a match
GROUP BY	Group rows for aggregates
AS	Provide a readable alias


Development sample data
SampleData.java is a Spring @Configuration class annotated with @Profile("dev"). Its CommandLineRunner runs at startup and:
1. Checks whether any articles or tags already exist.
2. If either table contains data, exits without inserting new sample records.
3. Otherwise creates the predefined tags, saves them, and associates them with sample articles.
The original seeder contained 12 articles and 15 tags. An expanded 100-article version was prepared separately. The number actually stored depends on which code is currently in your local project and whether seeding ran against an empty database. Use SELECT COUNT(*) FROM ARTICLES; rather than assuming the count.

To change or expand sample data, edit the seeder and verify the dev profile. If an existing persistent database already contains records, the emptiness check prevents reseeding; don't delete data without deciding what needs to be preserved.
Troubleshooting
Browser says “This site can't be reached” / connection refused
- Confirm Spring Boot is running and has not exited.
- Verify the console reports Tomcat started on port 8080.
- In Windows PowerShell, test whether something listens on the port:
netstat -ano | findstr :8080
- Try http://127.0.0.1:8080/h2-console on the same machine as the backend.
H2 console shows Whitelabel Error Page (404)
- Verify the URL /h2-console (or /h2-console/) and active server port.
- Look for H2 console available at '/h2-console' in the latest startup output.
- Confirm that the browser is reaching the same application instance whose logs you checked.
- Check the active Spring profile and console properties if the problem persists.
“Table ARTICLE not found”
Your schema uses plural table names. Run:
SELECT * FROM ARTICLES;
Also check the H2 left sidebar or query INFORMATION_SCHEMA.TABLES for actual names.
Article count is not 100
- Verify that the 100-article version of SampleData.java is present in your local code.
- Check that dev is active.
- Remember that the seeder exits when either articles or tags already exist.
- An in-memory H2 database is normally recreated on application restart.
Angular cannot load articles
- Keep the backend running.
- Confirm its port and the frontend's configured API base URL.
- Use browser Developer Tools → Network to inspect the failing HTTP request.
- Check the backend console for request-handling or CORS errors.
Development notes and roadmap
Keep this section updated as features are implemented. Ideas below are planned improvements, not claims of completed functionality.
Area	Possible next improvement	Status
Article discovery	Improve search and multiple-tag filters	To verify / enhance
REST API	Document confirmed endpoints, example requests and responses	Planned documentation
Database	Move to a durable database such as PostgreSQL	Future option
Pagination	Add server-side sorting and pagination	Future option
Testing	Add JUnit/Mockito and Angular tests	Future option
Reliability	Validate API inputs and standardize error responses	Future option
Deployment	Document production build/deployment	Future option


