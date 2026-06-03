# Transit Alert Dashboard

A full-stack transit operations app built as a JD practice project.  
Demonstrates: **Java 21 · Spring Boot 3.5 · MongoDB · React 18 · TypeScript · TailwindCSS · Docker · GitHub Actions**

---

## Architecture

```
┌──────────────────┐     REST/JSON     ┌─────────────────────────────┐
│  React + Vite    │  ←──────────────→  │  Spring Boot (port 8080)    │
│  (port 3000/5173)│                   │  /auth  /alerts  /routes     │
└──────────────────┘                   └────────────┬────────────────┘
                                                    │ Spring Data MongoDB
                                        ┌───────────▼────────────┐
                                        │   MongoDB 7 (port 27017)│
                                        └────────────────────────┘
```

## Service Map

| Service        | URL                                        | Notes                         |
| -------------- | ------------------------------------------ | ----------------------------- |
| Frontend       | http://localhost:3000                      | React SPA (nginx in Docker)   |
| Backend API    | http://localhost:8080/api                  | Spring Boot context path /api |
| Swagger UI     | http://localhost:8080/swagger-ui.html      | Bearer JWT auth required      |
| Actuator health| http://localhost:8080/actuator/health      | No auth required              |
| mongo-express  | http://localhost:8081                      | admin / changeme              |

## Demo Credentials

| Role     | Email                     | Password  |
| -------- | ------------------------- | --------- |
| Admin    | admin@transit.demo        | demo1234  |
| Operator | operator@transit.demo     | demo1234  |
| Viewer   | viewer@transit.demo       | demo1234  |

---

## Local Development (without Docker)

### Prerequisites
- Java 21, Maven 3.9+
- Node 20+, npm
- MongoDB 7 running on localhost:27017

### Backend
```bash
cd transit-alert-dashboard/backend
mvn spring-boot:run
# API at http://localhost:8080/api
```

### Frontend
```bash
cd transit-alert-dashboard/frontend
npm install
npm run dev
# App at http://localhost:5173
```

---

## Run with Docker Compose

```bash
cd transit-alert-dashboard

# First time: copy the env template
cp .env.example .env

# Build & start all services
docker compose up --build

# Stop
docker compose down
```

---

## Run Tests

### Backend
```bash
cd transit-alert-dashboard/backend
mvn verify
```

### Frontend
```bash
cd transit-alert-dashboard/frontend
npm test
```

### Storybook
```bash
cd transit-alert-dashboard/frontend
npm run storybook   # http://localhost:6006
```

---

## JD Skills Demonstrated

| JD Skill           | Where it appears                                             |
| ------------------ | ------------------------------------------------------------ |
| Java / Spring Boot | `backend/` — REST controllers, services, repositories       |
| MongoDB            | `@Document` entities, `MongoRepository`, compound indexes   |
| Spring Security    | JWT stateless auth, `OncePerRequestFilter`, BCrypt           |
| React + TypeScript | `frontend/src/` — strict TypeScript, React 18               |
| TanStack Query     | `useQuery` / `useMutation` in every page component          |
| TailwindCSS        | Utility-first styling throughout the frontend               |
| REST API design    | Paginated endpoints, `ProblemDetail` error responses        |
| Docker             | Multi-stage Dockerfiles for backend + frontend              |
| Docker Compose     | Full local stack with health checks and volume persistence  |
| GitHub Actions     | `.github/workflows/transit-alert-ci.yml`                    |
| Unit tests         | JUnit 5 + Mockito (backend), Vitest + RTL (frontend)        |
| OpenAPI / Swagger  | springdoc-openapi 2.8, JWT SecurityScheme configured        |
