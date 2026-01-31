# SaaS Billing Platform

Production-grade SaaS billing and subscription management backend built with Spring Boot.

---

## Overview

The SaaS Billing Platform is a backend system for managing subscriptions, billing cycles, payments, invoices, organizations, and analytics. It includes JWT authentication, file upload, email notifications, rate limiting, caching, pagination, sorting, filtering, complex queries, and OpenAPI documentation.

---

## Tech Stack

| Layer           | Technology                    |
|----------------|-------------------------------|
| Language       | Java 17 / 21                  |
| Framework      | Spring Boot 3.x               |
| Build          | Maven                         |
| API            | REST                          |
| Auth           | JWT (Bearer)                  |
| Database       | H2 (default), PostgreSQL/Neon|
| ORM            | Spring Data JPA (Hibernate)   |
| Messaging      | Apache Kafka                  |
| Caching        | Spring Cache (in-memory)      |
| Email          | JavaMailSender (SMTP)         |
| Docs           | Springdoc OpenAPI (Swagger)   |
| Config         | YAML                          |

---

## Project Metadata

| Field         | Value                                                |
|---------------|------------------------------------------------------|
| Group         | `com.project`                                        |
| Artifact      | `saas-billing-system`                                |
| Package       | `com.project.saas_billing_system`                   |
| Packaging     | JAR                                                  |

---

## Project Structure

```
src/main/java/com/project/saas_billing_system/
├── config/          Security, Kafka, Cache, Rate limit, Swagger
├── controller/v1/   REST: auth, organizations, subscriptions, invoices, payments, analytics, file, webhook
├── service/         Business logic
├── repository/      JPA repositories
├── model/           Entities (identity, billing, subscription, analytics, webhook)
├── dto/             Request/Response DTOs, PagedResponse
├── event/           Kafka event payloads
├── consumer/        Kafka consumers
├── producer/        Kafka event producer
├── exception/       GlobalExceptionHandler, custom exceptions
└── util/            ApiResponse, SecurityUtils, DateUtils
```

---

## Features

### Auth & Security

- User registration and login
- JWT token generation and validation
- Protected APIs (Bearer token)
- Role-based users (Organization-scoped)

### Core APIs

- **Organizations** – CRUD, list (paged & sorted)
- **Subscriptions** – Plans, create, cancel, list by organization
- **Invoices** – Create, get by id/number/organization, search (complex query, paged & sorted)
- **Payments** – Create, get by id/organization, revenue summary (complex query)
- **Analytics** – Usage by organization (period, timezone)
- **File upload** – Upload and download (e.g. invoice attachments)
- **Webhooks** – Payment webhook (no auth)

### Advanced Features

| Feature                  | Status |
|--------------------------|--------|
| Complex queries (JPQL)  | Yes – invoice search, revenue summary |
| Pagination & sorting     | Yes – organizations list, invoice search |
| Filtering                | Yes – status, date range on invoices/payments |
| Caching                  | Yes – organizations, subscription plans |
| File upload              | Yes – POST/GET /api/files |
| Email notification       | Yes – JavaMailSender, Kafka-triggered |
| API rate limiting        | Yes – per-IP, configurable window |
| Analytics APIs           | Yes – GET /api/analytics/usage/{orgId} |
| Global exception handling| Yes – GlobalExceptionHandler |
| Input validation         | Yes – @Valid, bean validation on DTOs |
| Swagger documentation    | Yes – /swagger-ui.html, /v3/api-docs |

### Integrations

- **Email SMTP** – Gmail SMTP (or other) in `application.yaml`
- **Kafka** – Events for payment, subscription, notification, analytics
- **Database** – H2 in-memory (default) or PostgreSQL/Neon (profile `postgres`)

---

## Getting Started

### Prerequisites

- Java 17 or 21
- Maven
- Kafka (optional; for event consumers)
- PostgreSQL or Neon (optional; use profile `postgres`)

### Run

```bash
mvn clean install
mvn spring-boot:run
```

Default profile uses H2 in-memory; no DB or Kafka required for basic API testing.

### Profiles

- **default** – H2 in-memory, `data.sql` seeds subscription plans
- **postgres** – PostgreSQL/Neon: set `SPRING_PROFILES_ACTIVE=postgres` and datasource env vars (see `application.yaml`)

### Mail (optional)

Copy `application-local.yaml.example` to `application-local.yaml` and set SMTP credentials, or configure `spring.mail.*` and `app.mail.from` in `application-local.yaml`. That file is gitignored.

### API Testing

- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **API docs:** `http://localhost:8080/v3/api-docs`
- Step-by-step flow and sample requests: see **API_TESTING_GUIDE.md**

---

## API Summary

| Area         | Endpoints |
|-------------|-----------|
| Auth        | POST /api/auth/register, POST /api/auth/login |
| Organizations | GET/POST/PUT /api/organizations, GET /api/organizations/{id}, GET /api/organizations/slug/{slug} (list is paged & sorted) |
| Subscriptions | GET /api/subscriptions/plans, POST/GET /api/subscriptions, GET /api/subscriptions/organization/{id}, POST /api/subscriptions/{id}/cancel |
| Invoices    | GET /api/invoices/search (paged, sorted, filters), GET/POST /api/invoices, GET /api/invoices/organization/{id} |
| Payments    | GET /api/payments/revenue-summary, GET/POST /api/payments, GET /api/payments/organization/{id} |
| Analytics   | GET /api/analytics/usage/{organizationId} |
| Files       | POST /api/files/upload, GET /api/files/download/{storedName} |
| Webhooks    | POST /api/webhooks/payment (no auth) |

---

## License

This project is for educational and portfolio purposes.
