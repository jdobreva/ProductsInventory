# 🛒 Product Inventory API Project

A simple Spring Boot REST API for managing product inventory, featuring JWT-based authentication and 
basic testing.

## 🚀 Features

* CRUD operations for products - 
* JWT-based authentication & authorization
* RESTful API design
* Integration & unit tests
* Built with Spring Boot, Spring Data JPA, and Spring Security

## 🏗️ Tech Stack

* Java 21+
* Spring Boot 3.5
* Spring Data JPA
* Spring Security (JWT)
* Spring Actuator
* Hibernate
* PostgreSQL
* Flyway
* Maven
* TestContainers

---

## 📂 Project Structure

```
src/
  ├── main
    ├── java 
       ├── com.adesso.products/
              ├── ProductsInventoryApplication.java
              ├── config                                  # API configuration beans and exceptions
                ├── security                              # Security configuration
                    ├── jwt                               # JWT security components
              ├── controller                              # REST controllers and global exception handler
              ├── converters                              # entity converters
              ├── service                                 # Business logic
              ├── repository                              # JPA repositories
              ├── entity                                  # Entities (Product, Availability, Users)
              ├── dto                                     # user request/response entities
              ├── exceptions                              # API exceptions
              └── utils                                   # Comfortable transformation utils
    └── resources                                         # API configurations of files - DB configuration, logging, context path
        ├── db                                            # DB scripts
            └── migrations                                # Flyway sql scripts for DB initialization
  └── test                                                # Unit and integration tests
      ├── java 
            └── com.adesso.products/......................# Test classes
      └── resources                                       # API configurations of files
        ├── db                                            # DB scripts
            └── migrations                                # Flyway sql scripts for DB initialization
```

---

## 🔐 Authentication

This API uses JWT for securing endpoints.

### User Registration

```
POST /auth/register
```

**Request:**

```json
{
  "username": "user",
  "password": "password",
  "role": "user role"
}
```
**Response:**

```
The user is successfully registered.
```

### JWT Token

```
POST /api/auth/token
```

**Request Header:**

```
Authorization: Basic Base64Enc.encode(username:password)
```

**Response:**

```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKZW5ueWEiLCJpYXQiOzOTg4NX0.a_ptBXfez37Qjj_tSnMQc3Xtn6JTAVZ8a1PYULkTjYw",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJKZW5ueWEiLCJpYXQiOjENDU0MzE4NX0.gHyXcJXq4LDdEBxUsxt4JgrzqwZcIUWYA2W1FJXJCMk",
    "time": "2026-03-26"
}
```

Use the token in headers of the API requests:

```
Authorization: Bearer <jwt-token>
```

---

## 📦 API Endpoints

### Products


| Method | Endpoint              | Description        |
| ------ | --------------------- | ------------------ |
| GET    | /api/v1/products      | Get all products   |
| GET    | /api/v1/products/{id} | Get product by ID  |
| POST   | /api/v1/products      | Create new product |
| PUT    | /api/v1/products/{id} | Update product     |
| DELETE | /api/v1/products/{id} | Delete product     |

### Availability


| Method | Endpoint               | Description                  |
| ------ | ---------------------- | ---------------------------- |
| GET    | /api/availability      | Get all availability records |
| PUT    | /api/availability/{id} | Update availability          |

---

## ⚙️ Running the Application

### Prerequisites

* Java 21+
* Maven
* Postgres database

### Steps

```bash
git clone https://github.com/your-username/product-inventory-api.git
cd product-inventory-api
mvn clean install
mvn spring-boot:run
```
---

## 🧪 Running Tests

```bash
mvn test
```

---

## 🗄️ Database

Default configuration uses PostgreSQL.


```
jdbc:postgresql://localhost:5432/productsinventory
```

## 🔧 Configuration

Example `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/productsinventory
spring.jpa.hibernate.ddl-auto=update
jwt.secret=your-secret-key
```

---

## 📌 Future Improvements

* Docker support
* Kafka messaging

---

## 📄 License

This project is open-source and available under the MIT License.

