# Einkaufen

## Overview

Einkaufen is a full-stack eCommerce platform built with a modern React frontend and a Java Spring Boot backend. The application provides a complete online shopping experience with customer storefront functionality, shopping cart management, secure authentication, order processing, payment integration, and an administrative dashboard for managing products and orders.

The project follows a client-server architecture, where the frontend communicates with the backend through REST APIs.

---

# Features

## Customer Features

* User registration and login
* JWT-based authentication and authorization
* Product browsing and product detail pages
* Shopping cart management
* Checkout workflow
* Order tracking and order history
* Product reviews and ratings
* Responsive user interface
* Payment integration

## Admin Features

* Admin dashboard
* Product management
* Order management
* Customer management
* Product creation and updates

## Backend Features

* RESTful API architecture
* JWT authentication and validation
* Secure password encryption using BCrypt
* Layered service architecture
* Repository pattern for database access
* Payment processing integration
* Custom exception handling

---

# Tech Stack

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

# Architecture Overview

The application uses a decoupled architecture where the frontend and backend operate independently.

```text
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
```

## Frontend Structure

The frontend is divided into:

* Customer-facing components
* Admin dashboard components
* Redux store, actions, and reducers
* Route-based navigation

### Main Routes

```text
/             -> Customer routes
/admin/*      -> Admin dashboard routes
```

## Backend Structure

The backend follows a layered architecture:

```text
Controller Layer
    ↓
Service Layer
    ↓
Repository Layer
    ↓
Database
```

### Backend Packages

* `Controller` → REST API endpoints
* `Service` → Business logic
* `Repo` → Database access layer
* `Model` → Application entities and models
* `Request` → Request DTOs
* `Response` → Response DTOs
* `Config` → JWT and security configuration
* `Exception` → Custom exception handling

---

# API Overview

## Authentication

```http
POST /auth/register
POST /auth/login
```

## Products

```http
GET    /api/products
GET    /api/products/{id}
POST   /api/admin/products
PUT    /api/admin/products/{id}
DELETE /api/admin/products/{id}
```

## Cart

```http
GET    /api/cart
PUT    /api/cart
DELETE /api/cartItem/delete/{cartItemId}
```

## Orders

```http
POST   /api/order/add
GET    /api/order
PUT    /api/admin/order/{orderId}/confirmed
```

## Payments

```http
POST /api/payment
GET  /api/payment/validate
```

> Some endpoints were inferred from the controller structure and may need adjustment depending on the final implementation.

---

# Project Structure

## Client

```text
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
```

## Server

```text
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
```

---

# Installation

## Prerequisites

Make sure the following are installed on your system:

* Node.js
* npm or yarn
* Java 17 or later
* Maven

---

# Frontend Setup

Navigate to the client directory:

```bash
cd client
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

Build the frontend for production:

```bash
npm run build
```

Preview the production build:

```bash
npm run preview
```

---

# Backend Setup

Navigate to the server directory:

```bash
cd server
```

Run the Spring Boot application:

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

---

# Development Scripts

## Client Scripts

| Command           | Description                        |
| ----------------- | ---------------------------------- |
| `npm run dev`     | Starts the Vite development server |
| `npm run build`   | Builds the frontend for production |
| `npm run lint`    | Runs ESLint checks                 |
| `npm run preview` | Previews the production build      |

---

# Authentication

The backend uses JWT authentication for securing protected routes.

### Authorization Header Format

```http
Authorization: Bearer <token>
```

### Authentication Features

* JWT token generation
* JWT validation filters
* Protected API routes
* Secure password hashing with BCrypt

---

# Payment Integration

The project integrates Razorpay for payment processing.

Features include:

* Payment link generation
* Payment verification
* Order and payment association

---

# Environment Variables

No environment variables were automatically detected, but the following configuration is recommended.

## Backend Environment Variables

```env
JWT_SECRET=your_secret_key

RAZORPAY_KEY=your_key
RAZORPAY_SECRET=your_secret

DATABASE_URL=your_database_url
DATABASE_USERNAME=your_username
DATABASE_PASSWORD=your_password
```

## Frontend Environment Variables

```env
VITE_API_BASE_URL=http://localhost:8080
```

---

# Deployment

## Frontend Deployment Options

* Vercel
* Netlify
* Firebase Hosting

## Backend Deployment Options

* AWS
* Render
* Railway
* DigitalOcean

### Recommended Production Improvements

* Store JWT secrets in environment variables
* Configure secure CORS policies
* Add centralized exception handling
* Implement request validation
* Add monitoring and logging
* Enable HTTPS in production

---

# Future Improvements

Potential future enhancements include:

* Advanced product search and filtering
* Wishlist functionality
* Inventory management
* Email notifications
* Analytics dashboard
* Image upload service
* Docker support
* Unit and integration testing
* CI/CD pipeline setup
* Swagger/OpenAPI documentation
* Role-based access control
* Performance optimization and caching

---

# Security Recommendations

To improve security for production environments:

* Move secrets and API keys to environment variables
* Add refresh token support
* Implement rate limiting
* Use HTTPS in production
* Improve authorization handling

---

# Contributing

Contributions are welcome.

## Steps to Contribute

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature/my-feature
```

3. Commit your changes

```bash
git commit -m "Add new feature"
```

4. Push your branch

```bash
git push origin feature/my-feature
```

5. Open a pull request

---

# License

Add your preferred license here.

Example:

```text
MIT License
```

---

# Author

Developed by Abinash.
