# Task Management REST API

A modern, production-style, yet beginner-friendly REST API for managing tasks built using **Spring Boot**, **Maven**, **Spring Data JPA**, and **PostgreSQL**. The application features layered architecture, robust bean validations, custom global exception handling, pagination/sorting, and interactive Swagger documentation.

---

## 🚀 Tech Stack

* **Java 17**
* **Spring Boot 3.2.5**
  * Spring Web (REST Controllers, ResponseEntity)
  * Spring Data JPA (Object-Relational Mapping, JpaRepository)
  * Bean Validation (jakarta.validation constraints)
* **PostgreSQL** (Database Persistence)
* **Lombok** (Boilerplate code reduction: `@Data`, `@Builder`, etc.)
* **Springdoc OpenAPI (Swagger UI)** (API Documentation & Testing)

---

## 📁 Package Structure

The project strictly adheres to a layered architecture:

```text
com.taskmanager
├── TaskManagerApplication.java    # Application Bootstrapper
├── config
│   └── SwaggerConfig.java         # Swagger UI Configurations
├── controller
│   └── TaskController.java        # REST Endpoint Handlers
├── dto
│   ├── TaskRequestDto.java        # Request validation DTO
│   └── TaskResponseDto.java       # Formatted API Response DTO
├── entity
│   ├── Status.java                # Enum: PENDING, COMPLETED
│   └── Task.java                  # JPA Database Entity
├── repository
│   └── TaskRepository.java        # Spring Data JPA Repository
├── service
│   ├── TaskService.java           # Service interface definition
│   └── impl
│       └── TaskServiceImpl.java   # Service business logic implementation
└── exception
    ├── ErrorResponse.java         # Standard Error JSON Model
    ├── GlobalExceptionHandler.java # Intercepts and formats errors
    └── ResourceNotFoundException.java # Custom 404 Exception
```

---

## ⚡ Key Features

1. **Full CRUD Operations**: Create, read (all or single), update, and delete tasks.
2. **Advanced Pagination & Sorting**: Fully configurable query parameters to paginate lists of tasks.
3. **Optional Filtering by Status**: Fetch only `PENDING` or `COMPLETED` tasks.
4. **Input Validation**: Rejects malformed requests (e.g. empty title, too long descriptions) with specific field-level validation errors.
5. **Global Exception Handling**: Automatically returns clean, standardized JSON error models for database misses and field validation errors.
6. **Self-Documenting API**: Direct Swagger UI generation showing active endpoints and mock schemas.

---

## ⚙️ Setup & Installation

### 1. Database Setup & Configuration

Follow these steps to set up your PostgreSQL database before running the application:

1. **Database Schema Execution**:
   * Connect to your PostgreSQL server using your database client (such as pgAdmin or psql CLI).
   * Run the following command to create the new database:
     ```sql
     CREATE DATABASE task_management_db;
     ```

2. **Update Database Credentials**:
   Open [application.properties](src/main/resources/application.properties) and update the datasource details with your local PostgreSQL credentials:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/task_management
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### 2. Build the Application
Navigate to the root directory where `pom.xml` is present and execute:
```bash
mvn clean compile
```

### 3. Run the Application
Run the main Spring Boot starter using:
```bash
mvn spring-boot:run
```
The application will start on port `8080`.

---

## 📖 API Request & Response Examples

### 1. Create a Task
* **Method**: `POST`
* **URL**: `http://localhost:8080/api/tasks`
* **Headers**: `Content-Type: application/json`
* **Request Body**:
```json
{
  "title": "Design Database Schema",
  "description": "Create the entity relation diagram and write PostgreSQL DDL scripts.",
  "status": "PENDING"
}
```

* **Success Response (201 Created)**:
```json
{
  "id": 1,
  "title": "Design Database Schema",
  "description": "Create the entity relation diagram and write PostgreSQL DDL scripts.",
  "status": "PENDING",
  "createdAt": "2026-05-18T22:15:30.123456"
}
```

---

### 2. Get All Tasks (with Pagination, Sorting, and Optional Filter)
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/tasks?status=PENDING&page=0&size=10&sortBy=createdAt&sortDir=desc`
* **Success Response (200 OK)**:
```json
{
  "content": [
    {
      "id": 1,
      "title": "Design Database Schema",
      "description": "Create the entity relation diagram and write PostgreSQL DDL scripts.",
      "status": "PENDING",
      "createdAt": "2026-05-18T22:15:30"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

---

### 3. Get Task By ID
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/tasks/1`
* **Success Response (200 OK)**:
```json
{
  "id": 1,
  "title": "Design Database Schema",
  "description": "Create the entity relation diagram and write PostgreSQL DDL scripts.",
  "status": "PENDING",
  "createdAt": "2026-05-18T22:15:30"
}
```

---

### 4. Update a Task
* **Method**: `PUT`
* **URL**: `http://localhost:8080/api/tasks/1`
* **Headers**: `Content-Type: application/json`
* **Request Body**:
```json
{
  "title": "Design Database Schema (Updated)",
  "description": "ER diagram finalized. Pushing DDL scripts to GitHub.",
  "status": "COMPLETED"
}
```

* **Success Response (200 OK)**:
```json
{
  "id": 1,
  "title": "Design Database Schema (Updated)",
  "description": "ER diagram finalized. Pushing DDL scripts to GitHub.",
  "status": "COMPLETED",
  "createdAt": "2026-05-18T22:15:30"
}
```

---

### 5. Delete a Task
* **Method**: `DELETE`
* **URL**: `http://localhost:8080/api/tasks/1`
* **Success Response (204 No Content)**:
*(No content returned)*

---

### 6. Error: Resource Not Found Exception Example
* **Method**: `GET`
* **URL**: `http://localhost:8080/api/tasks/99`
* **Error Response (404 Not Found)**:
```json
{
  "status": 404,
  "message": "Task not found with ID: 99",
  "timestamp": "2026-05-18T22:19:40.852103",
  "details": "uri=/api/tasks/99",
  "errors": null
}
```

---

### 7. Error: Validation Failure Example
* **Method**: `POST`
* **URL**: `http://localhost:8080/api/tasks`
* **Headers**: `Content-Type: application/json`
* **Request Body** (violating multiple constraints):
```json
{
  "title": "",
  "description": "Valid payload test",
  "status": null
}
```

* **Error Response (400 Bad Request)**:
```json
{
  "status": 400,
  "message": "Validation failed: Please correct the input fields",
  "timestamp": "2026-05-18T22:21:05.143256",
  "details": "uri=/api/tasks",
  "errors": {
    "title": "Title is required and cannot be blank",
    "status": "Status is required (PENDING or COMPLETED)"
  }
}
```

---

## 🔍 Interactive API Documentation (Swagger)

Once the application is running, open your web browser and navigate to:
👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

You can view fully formatted API definitions, schemas, and perform interactive requests against all endpoints right from the UI!
