package com.example.articles.config;

import com.example.articles.model.Article;
import com.example.articles.model.Tag;
import com.example.articles.repository.ArticleRepository;
import com.example.articles.repository.TagRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@Profile("dev")
public class SampleData {

    @Bean
    CommandLineRunner seedArticles(TagRepository tagRepository, ArticleRepository articleRepository) {
        return args -> {
            // Seed only when both tables are empty to avoid duplicate sample data.
            if (articleRepository.count() > 0 || tagRepository.count() > 0) {
                return;
            }

            List<String> names = List.of(
                    "AI", "LLM", "Machine Learning", "Java", "Spring Boot",
                    "Angular", "TypeScript", "Cloud", "DevOps", "Security",
                    "Database", "Kafka", "Microservices", "Testing", "Architecture"
            );

            Map<String, Tag> tags = tagRepository.saveAll(
                    names.stream().map(Tag::new).toList()
            ).stream().collect(Collectors.toMap(Tag::getName, Function.identity()));

            saveArticle(articleRepository, tags,
                    "Understanding LLM Applications",
                    "An introduction to prompts, tokens, model context, and common patterns for building useful language-model features.",
                    "AI", "LLM", "Machine Learning");

            saveArticle(articleRepository, tags,
                    "Building Retrieval-Augmented Search",
                    "Walk through document ingestion, chunking, embeddings, retrieval, and grounding an answer in source material.",
                    "AI", "LLM", "Database");

            saveArticle(articleRepository, tags,
                    "Spring Boot REST API Design",
                    "Explore controllers, DTO validation, status codes, pagination, and consistent API error handling.",
                    "Java", "Spring Boot", "Architecture");

            saveArticle(articleRepository, tags,
                    "Angular Components and State",
                    "Learn how to compose components, bind templates, and share state in a maintainable Angular application.",
                    "Angular", "TypeScript", "Architecture");

            saveArticle(articleRepository, tags,
                    "Kafka Consumer Reliability",
                    "Understand partition ordering, offset commits, retry strategies, idempotency, and dead-letter topics.",
                    "Kafka", "Java", "Microservices");

            saveArticle(articleRepository, tags,
                    "Database Query Performance",
                    "Use indexes, execution plans, and measured query timings to troubleshoot database bottlenecks.",
                    "Database", "Java", "Architecture");

            saveArticle(articleRepository, tags,
                    "Securing REST Endpoints",
                    "Review authentication, authorization, input validation, and API security fundamentals.",
                    "Security", "Spring Boot", "Java");

            saveArticle(articleRepository, tags,
                    "Testing Spring Boot Services",
                    "Write focused unit tests and integration tests using JUnit, Mockito, and realistic test data.",
                    "Testing", "Spring Boot", "Java");

            saveArticle(articleRepository, tags,
                    "Cloud-Native Service Deployment",
                    "Understand container images, health checks, configuration, and safe rolling deployments.",
                    "Cloud", "DevOps", "Microservices");

            saveArticle(articleRepository, tags,
                    "Designing Event-Driven Systems",
                    "Explore domain events, transactional outbox delivery, and eventual consistency between services.",
                    "Kafka", "Microservices", "Architecture");

            saveArticle(articleRepository, tags,
                    "TypeScript for Angular Applications",
                    "Use strong typing, interfaces, and observable streams to keep Angular code readable and safe.",
                    "TypeScript", "Angular", "Testing");

            saveArticle(articleRepository, tags,
                    "Responsible AI in Production",
                    "Discuss hallucination safeguards, evaluation, observability, and responsible use of retrieved context.",
                    "AI", "LLM", "Security");

            saveArticle(articleRepository, tags,
                    "Java 21 Virtual Threads in Practice",
                    "Compare virtual threads with platform threads and identify suitable blocking I/O workloads.",
                    "Java", "Architecture");

            saveArticle(articleRepository, tags,
                    "Java Records for API DTOs",
                    "Model immutable request and response data with records and understand validation boundaries.",
                    "Java", "Spring Boot");

            saveArticle(articleRepository, tags,
                    "Pattern Matching and Sealed Classes",
                    "Use sealed hierarchies and switch pattern matching to model domain variants safely.",
                    "Java", "Architecture");

            saveArticle(articleRepository, tags,
                    "CompletableFuture for Concurrent Workflows",
                    "Compose asynchronous tasks and handle timeouts, exceptions, and executor selection.",
                    "Java", "Architecture");

            saveArticle(articleRepository, tags,
                    "Java Streams and Collector Pitfalls",
                    "Understand lazy evaluation, duplicate map keys, stream reuse, and immutable results.",
                    "Java", "Testing");

            saveArticle(articleRepository, tags,
                    "Parallel Streams and Performance",
                    "Evaluate splitting, common-pool behavior, thread safety, and when parallelism hurts throughput.",
                    "Java", "Architecture");

            saveArticle(articleRepository, tags,
                    "Java Memory and Garbage Collection",
                    "Investigate heap usage, allocation pressure, and garbage collection pauses in services.",
                    "Java", "DevOps");

            saveArticle(articleRepository, tags,
                    "Java Flight Recorder for Diagnostics",
                    "Capture low-overhead runtime events to diagnose latency, contention, and allocation hotspots.",
                    "Java", "DevOps");

            saveArticle(articleRepository, tags,
                    "Designing Immutable Domain Objects",
                    "Protect invariants using constructors, defensive copies, and value-object patterns.",
                    "Java", "Architecture");

            saveArticle(articleRepository, tags,
                    "Java Exception Handling Strategies",
                    "Separate recoverable failures from programming defects and preserve useful error context.",
                    "Java", "Architecture");

            saveArticle(articleRepository, tags,
                    "Thread Safety with Concurrent Collections",
                    "Choose concurrent maps, blocking queues, and synchronization based on contention patterns.",
                    "Java", "Architecture");

            saveArticle(articleRepository, tags,
                    "Effective Java Optional Usage",
                    "Represent missing return values while avoiding Optional in unsuitable entity fields.",
                    "Java", "Architecture");

            saveArticle(articleRepository, tags,
                    "JVM Profiling in Production",
                    "Use thread dumps, heap dumps, and profiles to investigate real service incidents.",
                    "Java", "DevOps");

            saveArticle(articleRepository, tags,
                    "Spring Boot Dependency Injection",
                    "Understand constructor injection, bean lifecycles, component scanning, and circular dependencies.",
                    "Spring Boot", "Java");

            saveArticle(articleRepository, tags,
                    "Spring Boot Configuration Profiles",
                    "Manage environment-specific settings with profiles, externalized configuration, and secrets.",
                    "Spring Boot", "DevOps");

            saveArticle(articleRepository, tags,
                    "Spring Boot Actuator and Health Checks",
                    "Expose readiness, liveness, and application metrics without leaking sensitive details.",
                    "Spring Boot", "DevOps", "Cloud");

            saveArticle(articleRepository, tags,
                    "Spring Data JPA Entity Relationships",
                    "Map one-to-many and many-to-many associations and control ownership and cascading.",
                    "Spring Boot", "Database", "Java");

            saveArticle(articleRepository, tags,
                    "Avoiding the JPA N Plus One Problem",
                    "Recognize excessive lazy-loading queries and apply fetch joins and entity graphs.",
                    "Spring Boot", "Database");

            saveArticle(articleRepository, tags,
                    "Transaction Boundaries in Spring",
                    "Understand propagation, rollback rules, isolation, and self-invocation caveats.",
                    "Spring Boot", "Database", "Architecture");

            saveArticle(articleRepository, tags,
                    "Optimistic Locking with JPA",
                    "Prevent lost updates using version columns and clear conflict responses.",
                    "Spring Boot", "Database");

            saveArticle(articleRepository, tags,
                    "REST API Pagination and Filtering",
                    "Build stable paginated endpoints with sorting, validated filters, and useful metadata.",
                    "Spring Boot", "Architecture");

            saveArticle(articleRepository, tags,
                    "Exception Handling with Controller Advice",
                    "Return consistent problem details and map validation or domain errors to HTTP responses.",
                    "Spring Boot", "Java");

            saveArticle(articleRepository, tags,
                    "Spring Security Filter Chain",
                    "Trace authentication and authorization through filters and endpoint security rules.",
                    "Spring Boot", "Security");

            saveArticle(articleRepository, tags,
                    "JWT Validation in Spring Security",
                    "Validate token signatures, expiry, issuer, audience, and claims at API boundaries.",
                    "Spring Boot", "Security");

            saveArticle(articleRepository, tags,
                    "OAuth 2.0 and OpenID Connect Basics",
                    "Distinguish access tokens from ID tokens and understand authorization code flows.",
                    "Security", "Architecture");

            saveArticle(articleRepository, tags,
                    "API Rate Limiting Strategies",
                    "Protect APIs using per-client quotas, bursts, and clear retry signaling.",
                    "Security", "Microservices", "Architecture");

            saveArticle(articleRepository, tags,
                    "Idempotent REST API Design",
                    "Use idempotency keys and consistent persistence to avoid duplicate payment or order actions.",
                    "Spring Boot", "Database", "Architecture");

            saveArticle(articleRepository, tags,
                    "API Versioning Without Breaking Clients",
                    "Plan backward-compatible contracts and gradual endpoint deprecation.",
                    "Architecture", "Spring Boot");

            saveArticle(articleRepository, tags,
                    "Angular Standalone Components",
                    "Organize features using standalone components and explicit dependency imports.",
                    "Angular", "TypeScript");

            saveArticle(articleRepository, tags,
                    "Angular Signals and Derived State",
                    "Manage fine-grained UI state with signals, computed values, and effects.",
                    "Angular", "TypeScript");

            saveArticle(articleRepository, tags,
                    "Angular Reactive Forms",
                    "Implement typed controls, reusable validators, and server-side validation messages.",
                    "Angular", "TypeScript", "Testing");

            saveArticle(articleRepository, tags,
                    "Angular HTTP Interceptors",
                    "Centralize authentication headers, request tracing, and API error handling.",
                    "Angular", "TypeScript", "Security");

            saveArticle(articleRepository, tags,
                    "Angular Route Guards",
                    "Control navigation experiences while keeping actual authorization on the backend.",
                    "Angular", "Security");

            saveArticle(articleRepository, tags,
                    "Angular Lazy Loading for Performance",
                    "Split feature bundles and measure route-level loading improvements.",
                    "Angular", "Architecture");

            saveArticle(articleRepository, tags,
                    "RxJS SwitchMap and Cancellation",
                    "Avoid stale search responses with stream composition and cancellation semantics.",
                    "Angular", "TypeScript");

            saveArticle(articleRepository, tags,
                    "RxJS Error Handling Patterns",
                    "Place catchError and retry operators carefully to preserve expected observable behavior.",
                    "Angular", "TypeScript", "Testing");

            saveArticle(articleRepository, tags,
                    "Angular Component Testing",
                    "Test user interactions, dependency boundaries, and asynchronous templates.",
                    "Angular", "Testing");

            saveArticle(articleRepository, tags,
                    "TypeScript Generics for Reusable APIs",
                    "Write typed utility functions and API wrappers without unsafe any casts.",
                    "TypeScript", "Architecture");

            saveArticle(articleRepository, tags,
                    "TypeScript Discriminated Unions",
                    "Model distinct success and error outcomes with compiler-enforced narrowing.",
                    "TypeScript", "Architecture");

            saveArticle(articleRepository, tags,
                    "Accessible Angular User Interfaces",
                    "Support keyboard navigation, labels, focus states, and semantic HTML.",
                    "Angular", "Testing");

            saveArticle(articleRepository, tags,
                    "Frontend Performance Measurement",
                    "Monitor Core Web Vitals and improve change detection and rendering bottlenecks.",
                    "Angular", "DevOps");

            saveArticle(articleRepository, tags,
                    "Microservice Database Ownership",
                    "Avoid shared-table coupling by assigning data ownership to service boundaries.",
                    "Microservices", "Database", "Architecture");

            saveArticle(articleRepository, tags,
                    "Service Discovery and API Gateways",
                    "Understand routing, centralized policies, and service-to-service communication boundaries.",
                    "Microservices", "Cloud", "Architecture");

            saveArticle(articleRepository, tags,
                    "Circuit Breakers and Timeouts",
                    "Design resilient service calls with bounded retries, backoff, and fallback behavior.",
                    "Microservices", "Architecture");

            saveArticle(articleRepository, tags,
                    "Distributed Tracing Across Services",
                    "Correlate requests through traces, structured logs, and service-level metrics.",
                    "Microservices", "DevOps", "Cloud");

            saveArticle(articleRepository, tags,
                    "Saga Pattern for Distributed Transactions",
                    "Coordinate multi-step workflows with compensating actions and explicit failure handling.",
                    "Microservices", "Architecture", "Database");

            saveArticle(articleRepository, tags,
                    "Transactional Outbox with Spring Boot",
                    "Publish reliable domain events by persisting outbox records with business changes.",
                    "Spring Boot", "Kafka", "Database");

            saveArticle(articleRepository, tags,
                    "CQRS for Read and Write Workloads",
                    "Separate query models from commands when consistency and scaling needs justify it.",
                    "Architecture", "Microservices", "Database");

            saveArticle(articleRepository, tags,
                    "Eventual Consistency in User Workflows",
                    "Design UI and APIs for asynchronous state changes and reconciliation.",
                    "Microservices", "Architecture");

            saveArticle(articleRepository, tags,
                    "Domain-Driven Design Bounded Contexts",
                    "Define aggregates and contextual boundaries that align teams with business capabilities.",
                    "Architecture", "Microservices");

            saveArticle(articleRepository, tags,
                    "Monolith to Microservices Migration",
                    "Apply the strangler pattern and migrate by business capability rather than by table.",
                    "Architecture", "Microservices");

            saveArticle(articleRepository, tags,
                    "Kafka Topic Partitioning Decisions",
                    "Choose keys and partitions for ordering, throughput, and consumer parallelism.",
                    "Kafka", "Architecture");

            saveArticle(articleRepository, tags,
                    "Kafka Consumer Groups and Rebalancing",
                    "Understand partition assignment, rebalances, and the impact of slow consumers.",
                    "Kafka", "Microservices");

            saveArticle(articleRepository, tags,
                    "Kafka Exactly Once Semantics",
                    "Learn what Kafka transactions guarantee and where external side effects still need deduplication.",
                    "Kafka", "Architecture");

            saveArticle(articleRepository, tags,
                    "Kafka Schema Evolution",
                    "Evolve Avro or JSON schemas while preserving producer and consumer compatibility.",
                    "Kafka", "Microservices");

            saveArticle(articleRepository, tags,
                    "Kafka Retry and Dead Letter Topics",
                    "Separate transient failures from poison messages and design replay workflows.",
                    "Kafka", "Microservices");

            saveArticle(articleRepository, tags,
                    "Kafka Monitoring and Consumer Lag",
                    "Track lag, throughput, processing latency, and partition skew to spot problems.",
                    "Kafka", "DevOps");

            saveArticle(articleRepository, tags,
                    "Database Index Design",
                    "Choose composite indexes based on filter selectivity, sorting, and actual query plans.",
                    "Database", "Architecture");

            saveArticle(articleRepository, tags,
                    "SQL Joins and Execution Plans",
                    "Read joins and scan strategies to diagnose inefficient relational queries.",
                    "Database", "Architecture");

            saveArticle(articleRepository, tags,
                    "Database Connection Pool Tuning",
                    "Configure pool sizes and timeouts based on workload and database capacity.",
                    "Database", "Spring Boot", "DevOps");

            saveArticle(articleRepository, tags,
                    "Database Migrations with Flyway",
                    "Version schema changes and integrate migrations into deployment pipelines.",
                    "Database", "DevOps", "Spring Boot");

            saveArticle(articleRepository, tags,
                    "PostgreSQL JSONB Tradeoffs",
                    "Compare relational columns with JSONB for indexing, schema flexibility, and querying.",
                    "Database", "Architecture");

            saveArticle(articleRepository, tags,
                    "Redis Caching Patterns",
                    "Apply cache-aside strategies and plan expiration, invalidation, and stampede protection.",
                    "Database", "Microservices", "Architecture");

            saveArticle(articleRepository, tags,
                    "H2 Database for Local Development",
                    "Configure in-memory or file-based H2 and understand differences from production databases.",
                    "Database", "Spring Boot", "Testing");

            saveArticle(articleRepository, tags,
                    "Testcontainers for Integration Tests",
                    "Run database and messaging dependencies in containers for realistic automated tests.",
                    "Testing", "Spring Boot", "DevOps");

            saveArticle(articleRepository, tags,
                    "Mockito Stubbing and Verification",
                    "Mock external dependencies without over-mocking domain logic.",
                    "Testing", "Java");

            saveArticle(articleRepository, tags,
                    "Contract Testing Between Services",
                    "Validate API expectations to reduce consumer-provider integration regressions.",
                    "Testing", "Microservices");

            saveArticle(articleRepository, tags,
                    "Load Testing REST APIs",
                    "Measure latency percentiles, saturation, and throughput with representative traffic.",
                    "Testing", "DevOps", "Spring Boot");

            saveArticle(articleRepository, tags,
                    "CI Pipelines for Java and Angular",
                    "Automate builds, tests, dependency checks, and artifact publishing on pull requests.",
                    "DevOps", "Java", "Angular");

            saveArticle(articleRepository, tags,
                    "Docker Multi Stage Builds",
                    "Build smaller runtime images by separating compilation tools from production layers.",
                    "DevOps", "Cloud");

            saveArticle(articleRepository, tags,
                    "Kubernetes Readiness and Liveness",
                    "Configure probes to improve rollout safety without causing restart loops.",
                    "Cloud", "DevOps");

            saveArticle(articleRepository, tags,
                    "Kubernetes ConfigMaps and Secrets",
                    "Separate runtime configuration from images and limit exposure of secret values.",
                    "Cloud", "DevOps", "Security");

            saveArticle(articleRepository, tags,
                    "Blue Green and Canary Deployments",
                    "Compare controlled rollout approaches and plan fast rollback mechanisms.",
                    "Cloud", "DevOps", "Architecture");

            saveArticle(articleRepository, tags,
                    "Infrastructure as Code Foundations",
                    "Manage reproducible cloud resources with reviewable, version-controlled definitions.",
                    "Cloud", "DevOps");

            saveArticle(articleRepository, tags,
                    "AWS IAM Least Privilege",
                    "Design narrowly scoped permissions and avoid long-lived access keys.",
                    "Cloud", "Security");

            saveArticle(articleRepository, tags,
                    "Secrets Management in Cloud Systems",
                    "Rotate credentials and access them securely from deployed applications.",
                    "Cloud", "Security", "DevOps");

            saveArticle(articleRepository, tags,
                    "Embedding Models and Vector Similarity",
                    "Understand semantic embeddings, cosine similarity, and practical retrieval limitations.",
                    "AI", "Machine Learning", "Database");

            saveArticle(articleRepository, tags,
                    "Chunking Strategies for RAG Pipelines",
                    "Compare fixed-size and semantic chunking using retrieval quality measurements.",
                    "AI", "LLM", "Database");

            saveArticle(articleRepository, tags,
                    "RAG Evaluation and Grounded Answers",
                    "Measure retrieval relevance and answer faithfulness with representative test cases.",
                    "AI", "LLM", "Testing");

            saveArticle(articleRepository, tags,
                    "LLM Tool Calling in Spring AI",
                    "Expose validated backend capabilities through controlled model-invoked tools.",
                    "AI", "LLM", "Spring Boot");

            saveArticle(articleRepository, tags,
                    "Prompt Injection Defenses for RAG",
                    "Treat retrieved documents as untrusted input and restrict tool permissions.",
                    "AI", "LLM", "Security");

            saveArticle(articleRepository, tags,
                    "LLM Structured Outputs",
                    "Validate schema-constrained responses and handle malformed model results gracefully.",
                    "AI", "LLM", "Java");

            saveArticle(articleRepository, tags,
                    "AI Observability and Cost Tracking",
                    "Measure token consumption, response latency, failures, and evaluation scores.",
                    "AI", "LLM", "DevOps");

            saveArticle(articleRepository, tags,
                    "Model Context Protocol Fundamentals",
                    "Understand clients, servers, tools, resources, and authorization boundaries.",
                    "AI", "LLM", "Architecture");

            saveArticle(articleRepository, tags,
                    "Hybrid Search for Enterprise Knowledge",
                    "Combine keyword and vector retrieval and rerank results to improve relevance.",
                    "AI", "Database", "Machine Learning");

            saveArticle(articleRepository, tags,
                    "Building an AI Banking Assistant",
                    "Use authenticated transaction APIs, tool calling, and grounded responses for customer workflows.",
                    "AI", "Spring Boot", "Security");

            saveArticle(articleRepository, tags,
                    "Spring Boot Scheduling and Background Jobs",
                    "Use scheduled tasks responsibly with concurrency limits and failure handling.",
                    "Spring Boot", "Java", "DevOps");

            saveArticle(articleRepository, tags,
                    "Database Deadlocks and Transaction Isolation",
                    "Identify lock-order conflicts and choose isolation levels appropriate to the workload.",
                    "Database", "Java", "Architecture");
        };
    }

    private static void saveArticle(ArticleRepository repository, Map<String, Tag> tags,
                                    String title, String content, String... tagNames) {
        LinkedHashSet<Tag> articleTags = new LinkedHashSet<>();
        for (String tagName : tagNames) {
            Tag tag = tags.get(tagName);
            if (tag == null) {
                throw new IllegalArgumentException("Unknown article tag: " + tagName);
            }
            articleTags.add(tag);
        }
        repository.save(new Article(title, content, articleTags));
    }
}
