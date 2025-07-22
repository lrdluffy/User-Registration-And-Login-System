# 🚀 User Registration & Login System with Spring Boot, JWT, H2, and Docker

## 🔍 Overview

This project is a **User Registration and Login System** built with **Spring Boot**. It uses **JWT (JSON Web Tokens)** for authentication and **H2 Database** for data storage. The application is fully containerized using **Docker**🐳 and includes **Swagger UI** for API documentation.

### ✨ Features

- ✅ User Registration
- 🔐 User Login with JWT Authentication
- 🚫 Stateless API using JWT tokens
- 💾 In-memory H2 database for quick setup
- 🐳 Dockerized for easy deployment
- 📚 Swagger UI for API exploration and testing

## 🛠️ Tech Stack

- ☕ **Java 21+**
- ⚙️ **Spring Boot**
- 🔑 **Spring Security & JWT (JSON Web Token)**
- 🗄️ **H2 Database**
- 🐳 **Docker**
- 📚 **Swagger (Springdoc OpenAPI)**  

## 📡 API Endpoints

|Method                        |Endpoint                     |Description                     |
|------------------------------|-----------------------------|--------------------------------|
|POST                          |`/api/auth/register`         |📝 Register a new user          |
|POST                          |`/api/auth/login`            |🔑 Authenticate and receive JWT |
|GET                           |`/api/user/profile`          |👤 Get user profile (auth)      |
|PUT                           |`/api/user/profile`          |✏️ Update user profile (auth)   |

> After login, use the returned **JWT Token** in the `Authorization` header:  
> ` Authorization: Bearer <token>`

## 📝 API Documentation (Swagger UI)

This project includes **Swagger UI** for interactive API exploration and testing.

### 📍 Access Swagger UI

- Open in your browser after running the app: `http://localhost:8080/swagger-ui/index.html`    

---

### 🚀 Getting Started

#### ⚙️ Prerequisites

- ✅ Java 21+
- ✅ Maven
- ✅ Docker (optional)

---

#### 🖥️ Run Locally

##### 1️⃣ Clone the repository

```bash
git clone https://github.com/your-username/your-repo-name.git
cd your-repo-name
```

##### 2️⃣ Run the application

```bash
mvn clean install
mvn spring-boot:run
```

##### 3️⃣ Access H2 Console (Optional)

- 🌐 URL: `http://localhost:8080/h2-console`
- 🔗 JDBC URL: `jdbc:h2:mem:userRegisterationLoginSystem`
- 👤 Username: `sa`
- 🔑 Password: (leave blank)

---

#### 🐳 Run with Docker

##### 1️⃣ Build Docker Image

```bash
docker build -t springboot-jwt-auth .
```

##### 2️⃣ Run Docker Container

```bash
docker run -p 8080:8080 springboot-jwt-auth
```
---

#### ⚙️ Environment Variables (Optional)

Configure JWT and security settings in `application.properties`:

```properties
jwt.secret=your_jwt_secret_key
jwt.expiration=3600000
```
---

#### 📄 [License](./LICENSE)

This project is licensed under the **MIT License**.

---

#### 👨‍💻 Author

[**Strawhat Luffy**](https://github.com/lrduffy)

---

#### 🔧 Optional Improvements

- 📝 Add more detailed Swagger annotations
- 🛢️ Use PostgreSQL or MySQL in production
- 🐳 Deploy with Docker Compose
