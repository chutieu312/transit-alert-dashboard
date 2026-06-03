# JD Practice Plan — Full Stack Software Engineer (Transportation Tech)

---

## Original JD Summary

**Source:** Jobot (contract-to-hire, $55–60/hr)
**Client:** Anonymous mobility & transportation technology company
**Role:** Full Stack Software Engineer (Java | React | MongoDB | TypeScript)
**Contract:** 6 months to 1 year, potential hire

**Core Responsibilities:**
- Design, develop, implement, and maintain software applications
- Work independently on complex tasks requiring deep software engineering knowledge
- Design and refactor code for performance, scalability, and maintainability
- Conduct timely code reviews; identify logic discrepancies
- Create and maintain installation guides, user documentation, and troubleshooting manuals
- Support system configuration and application deployments (including after-hours)
- Promote software development best practices and testing strategies
- Perform root cause analysis and recommend solutions
- Lead maintenance engineering teams and provide SLA-level support
- Coordinate with Project Delivery teams for customer environment deployments
- Provide technical guidance and domain expertise to team members

---

## 1. JD Skill Extraction

### Required Technical Skills (inferred from title + responsibilities)
- **Java** — primary backend language
- **React** — primary frontend framework
- **MongoDB** — primary database
- **TypeScript** — typed JavaScript layer on frontend and/or backend
- Software development fundamentals and processes
- Software build and deployment processes
- Software requirements analysis and design
- Software debugging and root cause analysis
- Software documentation
- Software testing strategies
- Agile development principles and methodologies

### Preferred / Implied Technical Skills
- Spring Boot (standard Java web framework)
- REST API design
- JWT or OAuth-based authentication
- Docker / containerization
- CI/CD pipelines
- Cloud deployment (AWS, GCP, or Azure — public agency space often uses AWS)
- Code review practices

### Soft Skills & Collaboration Expectations
- Independent, self-directed execution
- Team leadership for maintenance engineering
- Written and verbal communication (documentation, troubleshooting guides)
- Professional reception of feedback
- SLA awareness and deadline management
- Ability to explain technical solutions to non-technical stakeholders

---

## 2. Skill Categories

| Category | Skills |
|---|---|
| **Backend** | Java 17, Spring Boot 3, Spring Security, Spring Data MongoDB, REST APIs, JWT auth |
| **Frontend** | React 18, TypeScript, Vite, TailwindCSS, React Query |
| **Database** | MongoDB, document modeling, indexes, aggregation pipeline |
| **Cloud** | AWS EC2 or Render.com (deployment target), environment variable management |
| **DevOps / CI-CD** | Docker, Docker Compose, GitHub Actions |
| **Testing** | JUnit 5, Mockito, Vitest, React Testing Library |
| **Security** | JWT authentication, Spring Security, CORS, input validation |
| **AI Tools / Automation** | GitHub Copilot (mentioned in JD context as productivity tool) |
| **Other** | Agile (Scrum), code review, technical documentation, Swagger/OpenAPI |

---

## 3. Recommended Mini Project

### 🚌 Transit Service Alert Dashboard

A lightweight internal operations tool for public transit agency operators to manage **service alerts**, **routes**, and **stops**. Operators log in, create alerts for disruptions (delays, outages, detours), assign them to affected routes, and mark them resolved.

**Why this fits the company:**
The client builds software for public agencies and municipalities managing transportation networks. A service alert system is a real, everyday feature in transit operations software — it's exactly the kind of product this company ships.

**What makes it ideal for interview practice:**
- Uses all four technologies in the job title: Java, React, MongoDB, TypeScript
- Small enough to build in a weekend or two
- Domain-authentic — shows you understand the client's business
- Demonstrates every category in the JD: backend, frontend, DB, auth, testing, CI/CD, docs

---

## 4. Why This Project Matches the JD

| JD Responsibility | How This Project Demonstrates It |
|---|---|
| Design & develop full-stack applications | Complete Java backend + React/TS frontend + MongoDB |
| Work independently on complex tasks | Self-contained project with no scaffolding shortcuts |
| Design for performance & scalability | MongoDB indexes on alertStatus + routeId, paginated API responses |
| Conduct code reviews | Pull-request workflow tracked in GitHub Actions CI |
| Create documentation | Swagger UI + README with architecture diagram |
| Support deployments | Docker Compose for local; Render or Railway for cloud |
| Root cause analysis | Structured error handling + logging (SLF4J / Logback) |
| Apply testing strategies | Unit tests (JUnit + Mockito), component tests (Vitest + RTL) |
| Lead with domain expertise | Transportation-specific language: routes, stops, alerts, SLAs |

---

## 5. Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│  Browser                                                    │
│  React 18 + TypeScript + Vite + TailwindCSS (port 5173)    │
│  → React Query for server state                             │
│  → React Router for SPA routing                             │
└────────────────────┬────────────────────────────────────────┘
                     │ HTTP / REST (JSON)
                     │ Authorization: Bearer <JWT>
┌────────────────────▼────────────────────────────────────────┐
│  Spring Boot 3 REST API (port 8080)                        │
│  → Spring Security + JWT filter chain                       │
│  → Spring Data MongoDB repositories                         │
│  → springdoc-openapi → Swagger UI at /swagger-ui.html       │
│  → SLF4J / Logback structured logging                       │
└────────────────────┬────────────────────────────────────────┘
                     │ Spring Data MongoDB
┌────────────────────▼────────────────────────────────────────┐
│  MongoDB 7 (port 27017)                                     │
│  Collections: users, routes, stops, alerts                  │
└─────────────────────────────────────────────────────────────┘

Developer Tools (via docker-compose):
  Mongo Express → http://localhost:8081   (DB admin UI)
  Swagger UI    → http://localhost:8080/swagger-ui.html
  Storybook     → http://localhost:6006  (npm run storybook — local only)
```

**CI/CD:** GitHub Actions — lint → test → build → Docker image push on every PR and main merge
**Cloud:** Render.com (free tier) — Java service + MongoDB Atlas free cluster + Vercel for frontend

---

## 6. Tech Stack Mapping

| JD Skill | Project Feature | How to Explain It in an Interview |
|---|---|---|
| Java | Spring Boot 3 REST API with service, repository, controller layers | "I built the backend in Java using Spring Boot 3, following a layered architecture: controller → service → repository. This keeps business logic separated from HTTP handling and data access." |
| React | React 18 SPA with login, dashboard, alert list, detail pages | "The frontend is a React 18 SPA. I used React Query to manage server state and avoid prop drilling. Each page is a separate route under React Router." |
| TypeScript | Typed API client, typed component props, shared DTO interfaces | "I wrote the entire frontend in TypeScript. Shared DTO types match the backend's response shapes, so any backend contract change is immediately caught at compile time." |
| MongoDB | Documents for routes, stops, alerts with embedded sub-documents | "I chose MongoDB because transit data has a flexible, hierarchical shape — a route embeds references to stops, an alert references one or more routes. MongoDB's document model maps naturally to that." |
| REST API Design | CRUD endpoints for routes, alerts; paginated list endpoints | "I designed RESTful endpoints following standard conventions: GET /api/alerts for list, POST /api/alerts to create, PATCH /api/alerts/:id to update status, DELETE for removal." |
| JWT Auth | Spring Security JWT filter, login endpoint, protected routes | "Authentication is stateless JWT. The login endpoint returns a signed token; a custom filter validates it on every subsequent request. No session state on the server." |
| Software Testing | JUnit + Mockito for service layer; Vitest + RTL for components | "I wrote unit tests for every service method using JUnit 5 and Mockito. On the frontend, I used Vitest and React Testing Library to test user interactions without a browser." |
| Docker | Multi-stage Dockerfiles for backend and frontend; docker-compose.yml | "Both services have multi-stage Dockerfiles. The compose file brings up the API, MongoDB, and Mongo Express with a single command — zero local install needed beyond Docker." |
| CI/CD | GitHub Actions: lint → test → Docker build on PRs and main | "I set up a GitHub Actions workflow that runs on every push. It lints, runs unit tests, and builds the Docker image. Failing tests block the merge." |
| Agile | GitHub Issues as tickets; PRs linked to issues; conventional commits | "I tracked work as GitHub Issues and used conventional commits so the changelog is auto-generated. PRs require CI to pass before merging — mirrors the team workflow in Agile shops." |
| Documentation | Swagger UI, README with service map, troubleshooting guide | "The Swagger UI lets anyone explore every endpoint and try authenticated calls without reading code. The README has a service map table with every URL and a local setup walkthrough." |
| Performance & Scalability | MongoDB compound index on alertStatus + routeId + createdAt | "I added compound indexes on the fields that appear most in query filters and sorts. Pagination prevents unbounded result sets. The backend can handle many simultaneous operators." |
| Security | Input validation (Jakarta Bean Validation), CORS config, no plain-text passwords | "Passwords are hashed with BCrypt. Inputs are validated with Jakarta annotations — malformed requests are rejected at the controller boundary. CORS is locked to the frontend origin." |
| Root Cause Analysis | Structured logging with request correlation IDs | "I added a servlet filter that injects a correlation ID into every request's MDC context. All log lines for a single request share the same ID, making root cause analysis fast." |
| Cloud Deployment | Render.com (API) + MongoDB Atlas (DB) + Vercel (frontend) | "For cloud deployment, I used Render for the Java service, MongoDB Atlas for the free-tier cluster, and Vercel for the React app. All three have free tiers and deploy from GitHub automatically." |

---

## 7. Step-by-Step Build Plan

### Phase 1 — Backend Foundation
**Goals:** Spring Boot project, JWT auth, User login endpoint, Route CRUD

Tasks:
1. Scaffold with Spring Initializr: `spring-boot-starter-web`, `spring-boot-starter-data-mongodb`, `spring-boot-starter-security`, `springdoc-openapi-starter-webmvc-ui`
2. Configure `application.yml` for MongoDB connection and JWT secret
3. Create `User` document + `UserRepository`
4. Implement `/api/auth/login` endpoint returning JWT
5. Create `JwtAuthFilter` (extends `OncePerRequestFilter`) and wire into `SecurityFilterChain`
6. Create `Route` document + `RouteController` + `RouteService` + `RouteRepository` (full CRUD)
7. Verify with curl or Postman

### Phase 2 — Database Layer
**Goals:** MongoDB schema finalized, indexes, seed data

Tasks:
1. Define all four collections: `users`, `routes`, `stops`, `alerts`
2. Add `@CompoundIndex` annotations: `(alertStatus, routeId)` on alerts, `(routeId)` on stops
3. Write a `DataSeeder` `@Component` (`@PostConstruct`) with sample routes, stops, and one admin user
4. Add `Stop` document embedded within `Route` or separate collection (separate is easier to query)
5. Add `Alert` document with fields: `title`, `description`, `severity`, `status` (ACTIVE/RESOLVED), `affectedRouteId`, `createdAt`, `resolvedAt`
6. Create `AlertController` + `AlertService` + `AlertRepository` with pagination

### Phase 3 — Frontend UI
**Goals:** React + TS app with login, dashboard, alert list, alert detail, create alert form

Tasks:
1. Scaffold with Vite: `npm create vite@latest frontend -- --template react-ts`
2. Install: `react-router-dom`, `@tanstack/react-query`, `axios`, `tailwindcss`
3. Create `AuthContext` (JWT token in localStorage) + `ProtectedRoute` wrapper
4. `LoginPage` — POST `/api/auth/login`, store token
5. `DashboardPage` — summary cards: active alerts count, route count
6. `AlertsPage` — paginated table with status badge (ACTIVE / RESOLVED)
7. `AlertDetailPage` — view details, resolve button (PATCH)
8. `CreateAlertPage` — form with route selector dropdown
9. `RoutesPage` — list all routes with stop count
10. Typed `apiClient` using Axios with `Authorization: Bearer` interceptor

### Phase 4 — Testing
**Goals:** Meaningful test coverage at both layers

Backend (JUnit 5 + Mockito):
1. `AlertServiceTest` — unit test create, resolve, pagination (mock `AlertRepository`)
2. `RouteServiceTest` — unit test CRUD (mock `RouteRepository`)
3. `AuthControllerTest` — verify bad credentials return 401
4. One `@SpringBootTest` integration test for the full auth + alert lifecycle

Frontend (Vitest + React Testing Library):
1. `LoginPage.test.tsx` — fill form, submit, check token stored
2. `AlertsPage.test.tsx` — renders list, status badge color
3. `CreateAlertForm.test.tsx` — validation errors shown on submit

### Phase 5 — Docker
**Goals:** Fully containerized stack runnable with `docker compose up`

Tasks:
1. `backend/Dockerfile` — multi-stage: Maven build → JRE 17 slim image
2. `frontend/Dockerfile` — multi-stage: `node:20` build → `nginx:alpine` serve
3. Root `docker-compose.yml`:
   - `mongodb` service (mongo:7)
   - `backend` service (depends_on: mongodb)
   - `frontend` service (depends_on: backend)
   - `mongo-express` service (depends_on: mongodb) — port 8081
4. Health checks on mongodb and backend
5. `.env.example` file with all required variables

### Phase 6 — CI/CD
**Goals:** GitHub Actions pipeline that blocks broken PRs

`.github/workflows/ci.yml`:
```
Trigger: push to main, pull_request

Jobs:
  backend-ci:
    - actions/checkout
    - Set up JDK 17
    - Run: mvn --no-transfer-progress verify
    - Upload: target/surefire-reports

  frontend-ci:
    - actions/checkout
    - Set up Node 20
    - npm ci
    - npm run lint
    - npm run test -- --run
    - npm run build
```

Branch protection rule: require both jobs green before merge.

### Phase 7 — Cloud Deployment
**Goals:** Publicly accessible demo URL to share in interview

Option A — Render + MongoDB Atlas + Vercel (recommended — all free):
1. MongoDB Atlas: free M0 cluster, get connection string
2. Render: new Web Service, point to `backend/`, set env vars, auto-deploy from main
3. Vercel: import `frontend/`, set `VITE_API_URL` to Render URL, auto-deploy

Option B — AWS (shows cloud skills explicitly):
1. ECR: push Docker images
2. ECS Fargate: run backend container
3. DocumentDB or MongoDB Atlas (Atlas is easier)
4. CloudFront + S3: serve frontend build
5. (More complex — reserve this explanation for interviews even if you deploy to Render)

### Phase 8 — Developer Exploration Tools
**Goals:** Any new team member can explore the running system without reading code

**API Explorer — Swagger UI (springdoc-openapi)**
- Dependency: `springdoc-openapi-starter-webmvc-ui`
- URL: `http://localhost:8080/swagger-ui.html`
- Config: add `@SecurityScheme` for Bearer JWT in `OpenApiConfig.java`
- Result: new hire can log in via Swagger, copy the token, authorize, and try every endpoint live

**Component Explorer — Storybook**
- Install: `npx storybook@latest init` in `frontend/`
- Run: `npm run storybook` → `http://localhost:6006`
- Stories to write: `AlertStatusBadge.stories.tsx`, `RouteCard.stories.tsx`, `LoginForm.stories.tsx`
- Note: runs locally only — do NOT add to docker-compose

**Database Admin UI — Mongo Express**
- Docker image: `mongo-express`
- URL: `http://localhost:8081`
- Config in docker-compose: `ME_CONFIG_MONGODB_SERVER=mongodb`, `ME_CONFIG_BASICAUTH_USERNAME`, `ME_CONFIG_BASICAUTH_PASSWORD`
- Result: browse documents, run queries, verify seed data — no Mongo shell needed

---

## 8. Developer Exploration Tools (Detail)

### Chosen Tools Summary

| Tool | Purpose | URL | How to Start |
|---|---|---|---|
| Swagger UI | Explore & test all REST endpoints with JWT auth | http://localhost:8080/swagger-ui.html | Auto-starts with backend |
| Mongo Express | Browse MongoDB collections and documents | http://localhost:8081 | `docker compose up` |
| Storybook | Develop and test React components in isolation | http://localhost:6006 | `npm run storybook` |

### Swagger UI Setup Notes
```java
// OpenApiConfig.java
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
            .components(new Components()
                .addSecuritySchemes("Bearer Authentication",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
```
New hire workflow: POST `/api/auth/login` in Swagger → copy token → click "Authorize" → paste token → all protected endpoints unlocked.

### Mongo Express docker-compose excerpt
```yaml
mongo-express:
  image: mongo-express
  ports:
    - "8081:8081"
  environment:
    ME_CONFIG_MONGODB_SERVER: mongodb
    ME_CONFIG_BASICAUTH_USERNAME: ${ME_USER:-admin}
    ME_CONFIG_BASICAUTH_PASSWORD: ${ME_PASS:-pass}
  depends_on:
    - mongodb
  restart: unless-stopped
```

---

## 9. Project Structure

```
transit-alert-dashboard/
├── backend/
│   ├── Dockerfile
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   ├── java/com/transitdemo/
│       │   │   ├── TransitAlertApplication.java
│       │   │   ├── auth/
│       │   │   │   ├── AuthController.java
│       │   │   │   ├── AuthRequest.java
│       │   │   │   ├── AuthResponse.java
│       │   │   │   ├── JwtAuthFilter.java
│       │   │   │   ├── JwtService.java
│       │   │   │   └── SecurityConfig.java
│       │   │   ├── config/
│       │   │   │   └── OpenApiConfig.java
│       │   │   ├── alerts/
│       │   │   │   ├── Alert.java
│       │   │   │   ├── AlertController.java
│       │   │   │   ├── AlertRepository.java
│       │   │   │   ├── AlertService.java
│       │   │   │   └── AlertStatus.java (enum)
│       │   │   ├── routes/
│       │   │   │   ├── Route.java
│       │   │   │   ├── RouteController.java
│       │   │   │   ├── RouteRepository.java
│       │   │   │   └── RouteService.java
│       │   │   ├── stops/
│       │   │   │   ├── Stop.java
│       │   │   │   ├── StopController.java
│       │   │   │   ├── StopRepository.java
│       │   │   │   └── StopService.java
│       │   │   ├── users/
│       │   │   │   ├── User.java
│       │   │   │   └── UserRepository.java
│       │   │   └── common/
│       │   │       ├── CorrelationIdFilter.java
│       │   │       └── GlobalExceptionHandler.java
│       │   └── resources/
│       │       └── application.yml
│       └── test/
│           └── java/com/transitdemo/
│               ├── alerts/
│               │   └── AlertServiceTest.java
│               ├── routes/
│               │   └── RouteServiceTest.java
│               └── auth/
│                   └── AuthControllerTest.java
├── frontend/
│   ├── Dockerfile
│   ├── index.html
│   ├── nginx.conf
│   ├── package.json
│   ├── tsconfig.json
│   ├── vite.config.ts
│   └── src/
│       ├── App.tsx
│       ├── main.tsx
│       ├── api/
│       │   └── client.ts          ← Axios instance with JWT interceptor
│       ├── components/
│       │   ├── Layout.tsx
│       │   ├── ProtectedRoute.tsx
│       │   ├── AlertStatusBadge.tsx
│       │   └── RouteCard.tsx
│       ├── context/
│       │   └── AuthContext.tsx
│       ├── pages/
│       │   ├── LoginPage.tsx
│       │   ├── DashboardPage.tsx
│       │   ├── AlertsPage.tsx
│       │   ├── AlertDetailPage.tsx
│       │   ├── CreateAlertPage.tsx
│       │   └── RoutesPage.tsx
│       ├── stories/
│       │   ├── AlertStatusBadge.stories.tsx
│       │   ├── RouteCard.stories.tsx
│       │   └── LoginForm.stories.tsx
│       ├── test/
│       │   ├── setup.ts
│       │   ├── LoginPage.test.tsx
│       │   ├── AlertsPage.test.tsx
│       │   └── CreateAlertForm.test.tsx
│       └── types/
│           └── index.ts           ← Shared DTO interfaces
├── docker-compose.yml
├── .env.example
├── .github/
│   └── workflows/
│       └── ci.yml
└── README.md
```

---

## 10. Local Setup Commands

```bash
# Prerequisites: Docker Desktop, Node 20, JDK 17 (for local dev without Docker)

# 1. Clone and configure environment
git clone https://github.com/yourname/transit-alert-dashboard.git
cd transit-alert-dashboard
cp .env.example .env          # Fill in JWT_SECRET and any credentials

# 2. Start the full stack (API + MongoDB + Mongo Express)
docker compose up --build

# 3. Services available after startup:
#   React Frontend   → http://localhost:3000
#   Spring Boot API  → http://localhost:8080
#   Swagger UI       → http://localhost:8080/swagger-ui.html
#   Mongo Express    → http://localhost:8081

# 4. Run frontend in dev mode (hot reload)
cd frontend
npm install
npm run dev                   # http://localhost:5173

# 5. Run Storybook (component explorer)
npm run storybook             # http://localhost:6006

# 6. Run backend tests only
cd backend
mvn test

# 7. Run frontend tests only
cd frontend
npm run test -- --run

# 8. Default seed credentials (created by DataSeeder)
#   Username: operator@transit.demo
#   Password: demo1234
```

---

## 11. Testing Plan

### Why Each Test Exists

| Test | Layer | Tool | JD Skill Demonstrated |
|---|---|---|---|
| `AlertServiceTest` — create, resolve, paginate | Backend unit | JUnit 5 + Mockito | Software testing, software debugging |
| `RouteServiceTest` — CRUD, not-found error | Backend unit | JUnit 5 + Mockito | Software testing, root cause analysis |
| `AuthControllerTest` — bad credentials → 401 | Backend unit | JUnit 5 + MockMvc | Security, software debugging |
| `AlertLifecycleIT` — login → create → resolve | Backend integration | `@SpringBootTest` + Flapdoodle embedded Mongo | Software build and deployment, testing |
| `LoginPage.test.tsx` — submit form, token stored | Frontend component | Vitest + RTL | Frontend testing |
| `AlertsPage.test.tsx` — renders badge, status color | Frontend component | Vitest + RTL | React component testing |
| `CreateAlertForm.test.tsx` — validation errors | Frontend component | Vitest + RTL | Input validation testing |

**Interview explanation:**
> "I structured tests in three layers. Unit tests verify business logic in isolation — I mock the repository so tests don't need a live database. Integration tests verify the full request-response cycle using an embedded MongoDB. Frontend tests use React Testing Library to test user behavior, not implementation details."

---

## 12. CI/CD Plan

### GitHub Actions Workflow (`.github/workflows/ci.yml`)

```
Triggers: push → main, pull_request → main

Job: backend-ci
  1. actions/checkout@v4
  2. actions/setup-java@v4  (Java 17, distribution: temurin)
  3. actions/cache@v4  (Maven ~/.m2)
  4. mvn --no-transfer-progress verify
  5. Upload artifact: surefire reports

Job: frontend-ci
  1. actions/checkout@v4
  2. actions/setup-node@v4  (Node 20)
  3. actions/cache@v4  (npm ~/.npm)
  4. npm ci
  5. npm run lint
  6. npm run test -- --run
  7. npm run build
```

**Branch protection rule:** Both jobs must pass green before any PR can merge into main.

**Why this CI/CD approach:**
> "The pipeline runs automatically on every push. Both the Java unit tests and the frontend tests gate every merge. Caching the Maven repository and npm cache cuts build time by 60–70%. This mirrors how real Agile teams enforce quality without slowing down development."

---

## 13. Cloud Deployment Plan

### Recommended: Render + MongoDB Atlas + Vercel (all free tier)

**Why this stack:**
- Zero cost for demo purposes
- Auto-deploy from GitHub on every merge to main
- MongoDB Atlas has a generous free M0 cluster (512 MB)
- Vercel handles the React build and CDN automatically

**Steps:**

1. **MongoDB Atlas**
   - Create free M0 cluster at cloud.mongodb.com
   - Whitelist all IPs (`0.0.0.0/0`) for demo
   - Create database user; copy connection string

2. **Render (Java API)**
   - New Web Service → connect GitHub repo → root: `backend/`
   - Build command: `mvn package -DskipTests`
   - Start command: `java -jar target/*.jar`
   - Environment variables: `SPRING_DATA_MONGODB_URI`, `JWT_SECRET`, `FRONTEND_URL`
   - Auto-deploy on push to main

3. **Vercel (React Frontend)**
   - Import GitHub repo → framework: Vite → root: `frontend/`
   - Environment variable: `VITE_API_URL=https://your-service.onrender.com`
   - Auto-deploy on push to main

**AWS Alternative (to mention in interview even if not deployed):**
> "If this were a production deployment, I'd containerize both services, push images to ECR, and run them on ECS Fargate. The database would be MongoDB Atlas or DocumentDB. The frontend would serve from S3 behind CloudFront. Secrets would live in AWS Secrets Manager, not environment variables."

---

## 14. Interview Talking Points

### Project Intro
> "I built a Transit Service Alert Dashboard — a full-stack internal tool for transit operators to manage service disruptions across routes and stops. I picked this domain because it mirrors exactly what this company builds for public agencies. The stack is Java Spring Boot, React with TypeScript, and MongoDB — the four technologies listed in the job posting."

### Backend
> "The API is a Spring Boot 3 application with a layered architecture: controller handles HTTP, service contains business logic, and repository talks to MongoDB via Spring Data. Authentication is stateless JWT — the login endpoint issues a signed token and every subsequent request passes through a custom filter that validates it."

### Frontend
> "The frontend is a React 18 SPA written entirely in TypeScript. I used React Query to manage server state — it handles caching, background refetching, and loading states so I don't have to write that logic manually. All API calls go through a typed Axios client with a request interceptor that attaches the JWT header automatically."

### Database
> "I used MongoDB because transit data has a hierarchical shape that maps naturally to documents. A route contains references to stops; an alert references one or more affected routes. I added compound indexes on the most common query patterns to keep list endpoints fast as the collection grows."

### Testing
> "I wrote tests at three levels: unit tests for service methods with mocked repositories, one integration test that spins up an embedded MongoDB and runs a full login-to-create-to-resolve lifecycle, and React Testing Library tests that verify user interactions without needing a browser."

### CI/CD
> "I set up a GitHub Actions workflow that runs on every pull request. It runs the Java tests and the frontend lint and tests. Branch protection rules require both to pass before merging. This mirrors the kind of quality gate I'd contribute to on an Agile team."

### Performance & Scalability
> "I added compound indexes on alertStatus and routeId so operator dashboards that filter by active alerts on a specific route don't do full collection scans. API responses are paginated — the client requests a page size and cursor, the backend never returns an unbounded result set."

### Documentation & Exploration
> "I configured Swagger UI so anyone can explore and test the API without reading the code. They log in through the Swagger UI, copy the token, authorize, and immediately try protected endpoints. I also set up Mongo Express so team members can inspect the live database without needing the Mongo CLI. Both are in docker-compose — no extra setup required."

### Connection to JD
> "What excited me about this role is that the company builds exactly this kind of software — operational tools for public agencies managing complex transportation networks. I wanted to practice by building something in that domain rather than a generic CRUD app."

---

## 15. Service Map (README Table)

| Service | URL | Description |
|---|---|---|
| React Frontend | http://localhost:3000 | Main web application |
| Spring Boot API | http://localhost:8080 | REST API |
| Swagger UI | http://localhost:8080/swagger-ui.html | Interactive API explorer |
| Mongo Express | http://localhost:8081 | MongoDB admin UI |
| Storybook | http://localhost:6006 | Component library (local: `npm run storybook`) |
| MongoDB | mongodb://localhost:27017 | Database (direct connection for GUI tools) |
