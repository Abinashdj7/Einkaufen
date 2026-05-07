# 🛒 Einkaufen

## Overview

Einkaufen is a full-stack eCommerce platform built with a modern React frontend and a Java Spring Boot backend. The application provides a complete online shopping experience including product browsing, cart management, secure authentication, order processing, payment integration, and an admin dashboard.

The project follows a client-server architecture where the frontend communicates with the backend through REST APIs.

---

# 🚀 Project Highlights

* Full-stack eCommerce platform (React + Spring Boot)
* Handles a catalog of **496 products**
* Secure **JWT-based authentication**
* **7 REST endpoints** for core functionality
* Average API response time: **~98ms**
* Backend tested with **15 JUnit tests (100% pass rate)**
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
* BCrypt password encryption
* Layered service architecture
* Repository pattern (database access)
* Payment processing integration (Razorpay)
* Custom exception handling
* Unit testing with JUnit 5

---

# 🧪 Testing

The backend includes unit tests to ensure stability and correctness of core business logic.

## Testing Stack

* JUnit 5
* Spring Boot Test
* Mockito (if applicable)

## Test Coverage

* 15 unit tests implemented
* 100% success rate (0 failures, 0 errors)
* Covers:
  * Service layer logic
  * Authentication flows
  * Order processing
  * Repository interactions

## Run Tests

cd server
./mvnw test

For Windows:

mvnw.cmd test

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

## Backend

* Spring Boot
* Spring Security
* JWT Authentication
* Maven
* Razorpay Payment Integration
* BCrypt Password Encoder

---

# 📁 Project Structure

## Client

client/
├── src/
│   ├── customer/components/
│   ├── App.tsx
│   ├── main.tsx
│   └── index.css
├── public/
├── package.json
├── tailwind.config.js
└── vite.config.ts

## Server

server/
├── src/main/java/com/Abinash/Nouveauecommerce/
│   ├── Config/
│   ├── Controller/
│   ├── Exception/
│   ├── Model/
│   ├── Repo/
│   ├── Request/
│   ├── Response/
│   └── Service/
├── src/main/resources/
│   └── application.properties
└── pom.xml

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

## Frontend Setup

cd client  
npm install  
npm run dev  

## Backend Setup

cd server  
./mvnw spring-boot:run  

---

# 🔐 Authentication

Authorization: Bearer <token>

---

# 💳 Payment Integration

Integrated with Razorpay.

---

# 🌍 Environment Variables

Backend:

JWT_SECRET=your_secret_key  
RAZORPAY_KEY=your_key  
RAZORPAY_SECRET=your_secret  

Frontend:

VITE_API_BASE_URL=http://localhost:8080  

---

# 🚀 Deployment

Frontend: Vercel, Netlify  
Backend: AWS, Render, Railway  

---

# ⚙️ GitHub Actions

This project uses three GitHub Actions workflows:

**1. Client CI (.github/workflows/client-ci.yml)**
Runs on push or pull request to master when files under e-commerce/Client/ change.
Sets up Node.js 20, installs dependencies with npm ci, runs TypeScript type checking
with tsc --noEmit, builds the app with npm run build, and uploads the dist folder
as an artifact for 7 days.

**2. Server CI (.github/workflows/server-ci.yml)**
Runs on push or pull request to master when files under e-commerce/Server/ change.
Spins up a MySQL 8.0 service container for tests, sets up Java 17 (Eclipse Temurin)
with Maven caching, runs mvn clean test, packages the app with mvn package, and
uploads the built JAR as an artifact for 7 days.

**3. Docker Build & Push (.github/workflows/docker.yml)**
Runs on every push to master. Logs into GitHub Container Registry (ghcr.io) using
the built-in GITHUB_TOKEN, builds and pushes both the client and server Docker images,
each tagged with both "latest" and the commit SHA. Uses GitHub Actions layer caching
to speed up repeated builds.

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
