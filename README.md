# 🛒 Einkaufen

## Overview

Einkaufen is a full-stack eCommerce platform built with a modern React frontend and a Java Spring Boot backend. The application provides a complete online shopping experience including product browsing, cart management, secure authentication, order processing, payment integration, and an admin dashboard.

The project follows a client-server architecture where the frontend communicates with the backend through REST APIs.

---

# 🚀 Project Highlights

* Full-stack eCommerce platform (React + Spring Boot)
* Handles a catalog of **496 products**
* Secure **JWT-based authentication** with a centralized Spring Security configuration
* **7 REST endpoints** for core functionality
* Average API response time: **~98ms**
* Backend tested with **32 JUnit tests** (20 unit/slice/context + 12 integration, 100% pass rate)
* Frontend covered by a **23-test Cypress E2E suite**
* **MySQL primary-replica replication** with automatic read/write routing and zero-data-loss failover
* CI/CD pipelines for build, unit tests, integration tests, E2E tests, and Docker image publishing
* Clean layered architecture (Controller → Service → Repository)

---

# ✨ Features

## Customer Features

* User registration and login
* JWT-based authentication and authorization
* Product browsing and detail pages
* Shopping cart management
* Checkout workflow
* Order tracking and history
* Product reviews and ratings
* Responsive UI
* Payment integration

## Admin Features

* Admin dashboard
* Product management (CRUD)
* Order management
* Customer management

## Backend Features

* RESTful API architecture
* JWT authentication and validation
* Centralized Spring Security configuration (CORS, stateless sessions, route authorization)
* BCrypt password encryption
* Layered service architecture
* Repository pattern (database access)
* MySQL primary-replica replication with `ReplicationRoutingDataSource` (writes → primary, reads → replica)
* Payment processing integration (Razorpay)
* Custom exception handling
* Unit, slice, and integration testing with JUnit 5, Mockito, and Spring Boot Test

---

# 🧪 Testing

The project has unit, integration, and end-to-end test coverage across both the backend and frontend.

## Testing Stack

* JUnit 5
* Mockito
* Spring Boot Test (`@WebMvcTest`, `@SpringBootTest`)
* H2 in-memory database (`test` profile)
* Cypress

## Backend Test Coverage

* **20 unit/slice/context tests** (Maven Surefire) — service layer logic (Mockito), controller slice tests, application context load
* **12 integration tests** (Maven Failsafe) — full-stack auth and product flows against an in-memory H2 database
* 32/32 tests passing

## Frontend E2E Coverage

* **23 Cypress tests** covering the home page and product listing/filtering, fully self-mocked (no backend required)

## Replication Failover Test

Requires the Docker stack to be running (`docker compose up -d` from `e-commerce/`).

Writes 100 rows to the primary, verifies they are readable on the replica, stops the primary container, confirms the replica stays readable with zero data loss, then restarts the primary.

Result: **PASS — zero data loss, replica survived primary failure** (tested: 100/100 rows intact, 0 ms replication lag at failover)

## Run Tests

Backend (from `e-commerce/Server`):

./mvnw test                                      # unit, slice, and context tests
./mvnw failsafe:integration-test failsafe:verify # integration tests

For Windows:

mvnw.cmd test
mvnw.cmd failsafe:integration-test failsafe:verify

Frontend E2E (from `e-commerce/Client`):

npm run e2e:run

Replication failover (from repo root, stack must be up):

python3 e-commerce/scripts/failover_test.py

---

# 📊 Performance Metrics

* Product catalog: **496 items**
* REST endpoints: **7 core endpoints**
* Average response time: **~98ms**
* Secure JWT authentication system

---

# 🧱 Architecture Overview

Frontend (React + Redux)
        |
        v
REST API Communication
        |
        v
Backend (Spring Boot)
        |
        +--> Authentication Layer (JWT)
        +--> Business Logic Services
        +--> Repository Layer
        +--> Payment Integration
        |
        v
ReplicationRoutingDataSource
        |
        +--> MySQL Primary  (writes / @Transactional)
        +--> MySQL Replica  (reads  / @Transactional readOnly)

---

# 💻 Tech Stack

## Frontend

* React
* TypeScript
* Redux
* Tailwind CSS
* Material UI
* Vite
* Axios
* React Router
* Cypress (E2E testing)

## Backend

* Spring Boot
* Spring Security
* JWT Authentication
* Maven
* MySQL 8.0 (primary-replica replication, GTID-based)
* Razorpay Payment Integration
* BCrypt Password Encoder
* H2 (in-memory test database)

---

# 📁 Project Structure

## Client

e-commerce/Client/
├── src/
│   ├── customer/components/
│   ├── App.tsx
│   ├── main.tsx
│   └── index.css
├── cypress/
├── public/
├── package.json
├── tailwind.config.js
└── vite.config.ts

## Server

e-commerce/Server/
├── src/main/java/com/Abinash/Nouveauecommerce/
│   ├── Config/
│   ├── Controller/
│   ├── Exception/
│   ├── Model/
│   ├── Repo/
│   ├── Request/
│   ├── Response/
│   └── Service/
├── src/test/java/com/Abinash/Nouveauecommerce/
│   ├── Controller/
│   ├── Integration/
│   └── Service/
├── src/main/resources/
│   └── application.properties
└── pom.xml

## Infrastructure

e-commerce/docker/mysql/
├── primary-init.sql      # creates replication user and test table on primary
└── setup-replication.sh  # wires GTID replication between primary and replica

e-commerce/scripts/
└── failover_test.py      # end-to-end replication failover test

---

# 🔌 API Overview

## Authentication

POST /auth/register  
POST /auth/login  

## Products

GET    /api/products  
GET    /api/products/{id}  
POST   /api/admin/products  
PUT    /api/admin/products/{id}  
DELETE /api/admin/products/{id}  

## Cart

GET    /api/cart  
PUT    /api/cart  
DELETE /api/cartItem/delete/{cartItemId}  

## Orders

POST   /api/order/add  
GET    /api/order  
PUT    /api/admin/order/{orderId}/confirmed  

## Payments

POST /api/payment  
GET  /api/payment/validate  

---

# ⚙️ Installation

## Prerequisites

* Node.js
* npm or yarn
* Java 17+
* Maven
* Docker (for the full stack with replication)

## Docker Setup (recommended)

cd e-commerce  
docker compose up -d  

Services started:
- Client: http://localhost:3001
- API server: http://localhost:8080
- MySQL primary: localhost:3306
- MySQL replica: localhost:3307

## Frontend Setup (dev)

cd e-commerce/Client  
npm install  
npm run dev  

## Backend Setup (dev)

cd e-commerce/Server  
./mvnw spring-boot:run  

---

# 🔐 Authentication

Authorization: Bearer <token>

---

# 💳 Payment Integration

Integrated with Razorpay.

---

# 🌍 Environment Variables

Copy `e-commerce/.env.example` to `e-commerce/.env` and fill in real values before running docker-compose. `.env` is gitignored — never commit real secrets.

Backend:

MYSQL_ROOT_PASSWORD=changeme123  
SPRING_DATASOURCE_USERNAME=root  
SPRING_DATASOURCE_PASSWORD=changeme123  
JWT_SECRET=your_secret_key  
CORS_ALLOWED_ORIGINS=http://localhost:3001  
ADMIN_EMAIL=admin@nouveau-ecommerce.com  
ADMIN_PASSWORD=changeme123  
RAZORPAY_API_KEY=your_key  
RAZORPAY_API_SECRET=your_secret  

Frontend:

VITE_API_BASE_URL=http://localhost:8080  

---

# 🚀 Deployment

Frontend: Vercel, Netlify  
Backend: AWS, Render, Railway  

---

# ⚙️ GitHub Actions

This project uses four GitHub Actions workflows:

**1. Client CI (.github/workflows/client-ci.yml)**
Runs on push or pull request to main when files under e-commerce/Client/ change.
Sets up Node.js 20, installs dependencies with npm ci, runs TypeScript type checking
with tsc --noEmit, builds the app with npm run build, and uploads the dist folder
as an artifact for 7 days.

**2. Server CI (.github/workflows/server-ci.yml)**
Runs on push or pull request to main when files under e-commerce/Server/ change.
Sets up Java 17 (Eclipse Temurin) with Maven caching, then runs three steps against
the in-memory H2 test database: unit/slice/context tests (mvn clean test),
integration tests (maven-failsafe-plugin), and a JAR build (mvn package -DskipTests),
uploading the built JAR as an artifact for 7 days.

**3. E2E CI (.github/workflows/e2e-ci.yml)**
Runs on push or pull request to main when files under e-commerce/Client/ change.
Sets up Node.js 20, installs dependencies, and runs the Cypress E2E suite against
the Vite dev server (npm run e2e:run). Uploads Cypress screenshots as an artifact
if any test fails.

**4. Docker Build & Push (.github/workflows/docker.yml)**
Runs on every push to main. Logs into GitHub Container Registry (ghcr.io) using
the built-in GITHUB_TOKEN, builds and pushes both the client and server Docker images,
each tagged with the lowercased repository owner plus "latest" and the commit SHA.
Uses GitHub Actions layer caching to speed up repeated builds.

---

# 🔮 Future Improvements

* Advanced search & filtering
* Wishlist functionality
* Inventory management
* Email notifications

---

# 🤝 Contributing

git checkout -b feature/my-feature  
git commit -m "Add new feature"  
git push origin feature/my-feature  

---

# 📄 License

MIT License

---

# 👨‍💻 Author

Developed by Abinash
