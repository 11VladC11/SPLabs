# Design Patterns Lab 4 - Chain of Responsibility and Command

This lab implements a RESTful Web service for managing books using the **Command** pattern and **Chain of Responsibility** pattern (via servlet filters).

## Architecture Overview

### Design Patterns Implemented

1. **Chain of Responsibility Pattern**
   - Implemented via `RequestLoggingFilter`
   - Logs all HTTP requests before and after processing
   - Follows the servlet filter chain pattern

2. **Command Pattern**
   - **Client**: `BooksController` (creates and executes commands)
   - **Invoker**: `CommandExecutor` (manages synchronous and asynchronous execution)
   - **Receiver**: `BooksService` (contains business logic)
   - **Commands**: 
     - `GetAllBooksCommand`
     - `GetBookByIdCommand`
     - `CreateBookCommand`
     - `UpdateBookCommand`
     - `DeleteBookCommand`

### Processing Types

- **Synchronous Processing**: GET, PUT, DELETE operations
  - Immediate response with results
  - Suitable for fast operations

- **Asynchronous Processing**: POST operations
  - Returns HTTP 202 (Accepted) immediately
  - Processes in background using ThreadPool (Executors.newFixedThreadPool)
  - Client can poll status via `/books/status/{requestId}`

## Project Structure

```
src/main/java/com/designpatterns/lab/
├── controller/
│   └── BooksController.java          # REST endpoints
├── service/
│   └── BooksService.java             # Business logic layer
├── command/
│   ├── Command.java                  # Command interface
│   ├── CommandContext.java           # Dependency injection for commands
│   ├── CommandExecutor.java          # Invoker (sync/async execution)
│   ├── GetAllBooksCommand.java
│   ├── GetBookByIdCommand.java
│   ├── CreateBookCommand.java
│   ├── UpdateBookCommand.java
│   └── DeleteBookCommand.java
├── filter/
│   └── RequestLoggingFilter.java     # Chain of Responsibility
├── model/
│   └── Book.java                     # Entity model
└── DesignPatternsLabApplication.java # Spring Boot main
```

## REST API Endpoints

### 1. GET /books
Returns all books
- **Processing**: Synchronous
- **Response**: 200 OK with list of books

### 2. GET /books/{id}
Returns a specific book by ID
- **Processing**: Synchronous
- **Response**: 200 OK or 404 Not Found

### 3. POST /books
Creates a new book (asynchronous)
- **Processing**: Asynchronous (simulates external ISBN service call)
- **Response**: 202 Accepted with requestId
- **Body**: 
```json
{
  "title": "Book Title",
  "author": "Author Name",
  "isbn": "978-1234567890"
}
```

### 4. PUT /books/{id}
Updates an existing book
- **Processing**: Synchronous
- **Response**: 200 OK or 404 Not Found
- **Body**: Same as POST

### 5. DELETE /books/{id}
Deletes a book
- **Processing**: Synchronous
- **Response**: 204 No Content or 404 Not Found

### 6. GET /books/status/{requestId}
Check status of asynchronous request
- **Response**: 
```json
{
  "requestId": "uuid",
  "status": "PENDING|COMPLETED|FAILED|NOT_FOUND",
  "result": {...},
  "error": "error message if failed"
}
```

## Running the Application

### Using Gradle (if Gradle is installed):
```bash
./gradlew bootRun
```

### Using Java directly:
```bash
# Build
./gradlew build

# Run
java -jar build/libs/design-patterns-lab-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## Testing the API

### Using curl:

```bash
# Get all books
curl http://localhost:8080/books

# Get book by ID
curl http://localhost:8080/books/1

# Create a new book (async)
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Design Patterns","author":"Gang of Four","isbn":"978-0201633610"}'

# Check async request status
curl http://localhost:8080/books/status/{requestId}

# Update a book
curl -X PUT http://localhost:8080/books/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Updated Title","author":"Updated Author","isbn":"978-0000000000"}'

# Delete a book
curl -X DELETE http://localhost:8080/books/1
```

## Key Design Decisions

### 1. Why Command Pattern?
- **Decouples** trigger (controller) from execution (business logic)
- Enables **flexible execution strategies** (sync vs async)
- Easy to add new operations without modifying controller
- Supports **undo/redo** functionality (future enhancement)

### 2. Synchronous vs Asynchronous Processing

**Synchronous** (GET, PUT, DELETE):
- Fast operations
- Client needs immediate response
- No external dependencies

**Asynchronous** (POST):
- Time-consuming operations (external ISBN service)
- Uncommitted SLA
- Better user experience (no blocking)
- Scalability for high-load scenarios

### 3. Why Service Layer?
- **Single Responsibility Principle**: Controller handles HTTP, Service handles business logic
- **Testability**: Can test business logic independently
- **Reusability**: Service methods can be called from multiple controllers

### 4. Chain of Responsibility via Filter
- **Logging**: Centralized request/response logging
- **Non-invasive**: No need to modify controller methods
- **Extensible**: Can add authentication, rate limiting, etc.

## Benefits of This Architecture

1. **Separation of Concerns**: Controller, Service, Command layers are distinct
2. **Flexibility**: Easy to switch between sync/async execution
3. **Maintainability**: Business logic changes don't affect controller
4. **Scalability**: ThreadPool handles concurrent async requests
5. **Monitoring**: Comprehensive request logging via filter chain

## Technologies Used

- Java 17
- Spring Boot 3.1.5
- Spring Web (REST)
- Gradle build system
- SLF4J for logging
- ExecutorService for async processing

## Lab Questions Answered

**Q2**: What is a good candidate to hook logging responsibility?
**A**: Servlet Filter - implements Chain of Responsibility pattern

**Q3**: This is an example of what design pattern?
**A**: Chain of Responsibility pattern

**Q7**: Why decouple business logic from controller?
**A**: Single Responsibility Principle - Controller handles HTTP concerns, Service handles business logic. Improves testability, maintainability, and reusability.

**Q8**: Processing types appropriate for each method:
- GET /books: **Synchronous** (fast, read-only)
- GET /books/{id}: **Synchronous** (fast, read-only)
- POST /books: **Asynchronous** (requires external ISBN service with uncommitted SLA)
- PUT /books/{id}: **Synchronous** (fast update)
- DELETE /books/{id}: **Synchronous** (fast delete)

**Q15**: Chosen approach - **Approach 2: CommandExecutor**
**Reason**: More flexible and follows SOLID principles better. Allows runtime selection of execution strategy without class hierarchy complexity.