# Spring Boot JPA

## Table of Contents

- [Project Structure](#project-structure)
- [Project Overview](#project-overview)
- [Architecture Overview](#architecture-overview)
    - [Class Diagram](#class-diagram)
    - [Sequence Diagram - Book Creation Flow](#sequence-diagram---book-creation-flow)
- [Layer Details](#layer-details)
    - [Entities and Domain Objects](#entities-and-domain-objects)
    - [Repository Layer](#repository-layer)
    - [Service Layer](#service-layer)
    - [Mapper Layer](#mapper-layer)
    - [Presentation (Controller) Layer](#presentation-controller-layer)
- [Testing Strategy](#testing-strategy)
    - [Integration Tests](#integration-tests)
    - [Repository Integration Tests](#repository-integration-tests)
- [API Examples](#api-examples)
    - [Authors](#authors)
    - [Books](#books)
    - [Sample Requests](#sample-requests)
- [Database Configuration](#database-configuration)

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── api/
│   │               ├── ApiApplication.java          # Main Spring Boot Application
│   │               ├── config/
│   │               │   └── MapperConfig.java       # ModelMapper Configuration
│   │               ├── controllers/
│   │               │   ├── AuthorController.java    # REST endpoints for Author
│   │               │   └── BookController.java      # REST endpoints for Book
│   │               ├── domain/
│   │               │   ├── dto/                    # Data Transfer Objects
│   │               │   │   ├── AuthorDto.java
│   │               │   │   └── BookDto.java
│   │               │   └── entity/                 # JPA Entities
│   │               │       ├── AuthorEntity.java
│   │               │       └── BookEntity.java
│   │               ├── mappers/
│   │               │   ├── Mapper.java             # Generic Mapper Interface
│   │               │   └── impl/                   # Mapper Implementations
│   │               │       ├── AuthorMapperImpl.java
│   │               │       └── BookMapperImpl.java
│   │               ├── repositories/               # Spring Data JPA Repositories
│   │               │   ├── AuthorRepository.java
│   │               │   └── BookRepository.java
│   │               └── services/
│   │                   ├── AuthorService.java      # Service Interfaces
│   │                   ├── BookService.java        # Service Implementations
│   │                   └── impl/
│   │                       ├── AuthorServiceImpl.java
│   │                       └── BookServiceImpl.java
│   └── resources/
│       └── application.properties                  # Application Configuration
├── test/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── api/
│   │               ├── ApiApplicationTests.java
│   │               ├── controllers/                # Integration Tests
│   │               │   ├── AuthorControllerIntegrationTests.java
│   │               │   └── BookControllerIntegrationTests.java
│   │               ├── repositories/               # Repository Tests
│   │               │   ├── AuthorEntityRepositoryIntegrationTest.java
│   │               │   └── BookEntityRepositoryIntegrationTest.java
│   │               └── util/                      # Test Utilities
│   │                   ├── Constants.java
│   │                   └── TestDataUtil.java
│   └── resources/
│       └── application.properties                 # Test Configuration
```

### Project Overview

**Key Features:**

- Full CRUD operations for entities
- Custom repository queries (derived, JPQL, native SQL)
- Entity-DTO mapping with ModelMapper
- PostgreSQL database integration
- In-memory H2 database for testing
- Integration tests with MockMvc
- Sequence-based ID generation
- Cascading operations in relationships

### Architecture Overview

The application follows a clean layered architecture:

- **Presentation Layer**: Controller with REST endpoints exposing API operations
- **Service Layer**: Business logic with implicit transactions
- **Repository Layer**: Data access with custom queries
- **Entity Layer**: JPA entities representing database tables
- **DTO Layer**: Data transfer objects for API communication
- **Mapper Layer**: Converts between entities and DTOs

#### Class Diagram

```mermaid
classDiagram
    class ApiApplication {
        +main(String[] args): void
    }

    class AuthorController {
        -authorService: AuthorService
        -authorMapper: AuthorMapperImpl
        +createAuthor(AuthorDto): ResponseEntity<AuthorDto>
    }

    class BookController {
        -bookMapper: BookMapperImpl
        -bookService: BookService
        +createBook(String, BookDto): ResponseEntity<BookDto>
    }

    class AuthorService {
        <<interface>>
        +createAuthor(AuthorEntity): AuthorEntity
    }

    class AuthorServiceImpl {
        +createAuthor(AuthorEntity): AuthorEntity
    }

    class BookService {
        <<interface>>
        +createBook(BookEntity): BookEntity
    }

    class BookServiceImpl {
        +createBook(BookEntity): BookEntity
    }

    class AuthorRepository {
        <<interface>>
        +ageLessThan(int): Iterable<AuthorEntity>
        +findAuthorsAgeGreaterThan(int): Iterable<AuthorEntity>
        +findAuthorByName(String): Iterable<AuthorEntity>
    }

    class BookRepository {
        <<interface>>
        +CRUD operations
    }

    class MapperInterface {
        <<interface>>
        +mapFrom(DTO): Entity
        +mapTo(Entity): DTO
    }

    class AuthorMapperImpl {
        +mapFrom(AuthorDto): AuthorEntity
        +mapTo(AuthorEntity): AuthorDto
    }

    class BookMapperImpl {
        +mapFrom(BookDto): BookEntity
        +mapTo(BookEntity): BookDto
    }

    AuthorController --> AuthorService
    BookController --> BookService
    AuthorService <|.. AuthorServiceImpl
    BookService <|.. BookServiceImpl
    AuthorServiceImpl --> AuthorRepository
    BookServiceImpl --> BookRepository
    AuthorRepository <|.. CrudRepository
    BookRepository <|.. CrudRepository
    AuthorMapperImpl ..|> MapperInterface
    BookMapperImpl ..|> MapperInterface
    AuthorController --> AuthorMapperImpl
    BookController --> BookMapperImpl
    AuthorMapperImpl --> ModelMapper
    BookMapperImpl --> ModelMapper
```

#### Sequence Diagram - Book Creation Flow

```mermaid
sequenceDiagram
    participant Client
    participant BookController
    participant BookService
    participant BookServiceImpl
    participant BookMapper
    participant BookRepository
    participant AuthorRepository
    Client ->> BookController: PUT /books/{isbn} with BookDto
    BookController ->> BookController: Override ISBN from path
    BookController ->> BookMapper: mapFrom(BookDto)
    BookMapper ->> BookMapper: ModelMapper conversion
    BookController ->> BookService: createBook(BookEntity)
    BookService ->> BookServiceImpl: createBook(BookEntity)
    BookServiceImpl ->> BookRepository: save(BookEntity)
    BookRepository ->> Database: INSERT/UPDATE books table
    Note over BookRepository, Database: Cascading saves if author new
    BookServiceImpl ->> BookController: Return saved BookEntity
    BookController ->> BookMapper: mapTo(BookEntity)
    BookMapper ->> BookController: BookDto with nested AuthorDto
    BookController ->> Client: ResponseEntity<BookDto>
```

### Layer Details

#### Entities and Domain Objects

**AuthorEntity:**

- Primary key: `id` (Long, auto-generated via SEQUENCE)
- Fields: `name` (String), `age` (Integer), `details` (String - computed field)
- Annotations: `@Entity`, `@Table(name="authors")`, `@GeneratedValue`

**BookEntity:**

- Primary key: `isbn` (String - natural key, manually provided)
- Fields: `title` (String)
- Relationship: `@ManyToOne` with `AuthorEntity` using `CascadeType.ALL` and `@JoinColumn`
- Annotations: `@Entity`, `@Table(name="books")`

**DTOs (Data Transfer Objects):**

- **AuthorDto**: Mirrors AuthorEntity fields except no ID auto-gen
- **BookDto**: Includes nested `authorDto` for API responses
- Purpose: Decouple API from internal entities, control data exposure

#### Repository Layer

**Features:**

- Extends Spring Data JPA `CrudRepository<T, ID>`
- Provides automatic CRUD operations:
    - `save(entity)`: Insert or update entity
    - `findById(id)`: Retrieve by ID
    - `findAll()`: Get all entities
    - `deleteById(id)`: Remove by ID
    - `count()`: Count total entities
    - `existsById(id)`: Check existence
- Custom query methods: Derived queries, JPQL (@Query with JPQL), Native SQL (@Query with nativeQuery=true)

**AuthorRepository:**

- Supports basic repository with standard CRUD operations
- Returns `Iterable<AuthorEntity>` for collection queries
- Custom query methods
    - Method naming conventions: `ageLessThan(int)` derives `SELECT * FROM authors WHERE age < ?`
    - JPQL queries use entity names: `SELECT a FROM AuthorEntity a WHERE a.age > ?1`
    - Native queries use table names: `SELECT * FROM authors WHERE name = ?`

**BookRepository:**

- Supports basic repository with standard CRUD operations

#### Service Layer

**AuthorService/BookService:**

- Simple create operations currently, extensible for business logic

**AuthorServiceImpl/BookServiceImpl:**

- Annotated with `@Service` for Spring bean registration
- Dependency injection via constructor injection (recommended)
- Methods are implicitly transactional through Spring Data JPA
- Cascade operations trigger when saving related entities

#### Mapper Layer

**Purpose:** Convert between Entity and DTO objects

**Mapper Interface:** Generic interface `Mapper<A, B>` with mapFrom(B) -> A and mapTo(A) -> B methods

**Custom Mapping:** AuthorMapperImpl enhances DTO with computed "details" field before entity conversion

**Implementations:**

- **AuthorMapperImpl:** Maps AuthorEntity ↔ AuthorDto
- **BookMapperImpl:** Maps BookEntity ↔ BookDto (nested AuthorEntity ↔ AuthorDto)

**ModelMapper Configuration:**

- Created as Spring `@Bean` in MapperConfig
- `MatchingStrategy.LOOSE` allows flexible field mapping, ignoring minor name mismatches
    - Skips null fields during mapping to avoid overwriting existing values
    - Supports deep/nested object mapping automatically
- Automatic nested object mapping for complex DTOs
- Handles null inputs gracefully

#### Presentation (Controller) Layer

**RestController Annotations:** `@RestController`: Combines @Controller and @ResponseBody

**AuthorController:**

- POST `/authors`: Accepts AuthorDto, returns created AuthorDto
- Maps DTO → Entity → save → Entity → DTO → JSON
- HTTP 201 Created status

**BookController:**

- PUT `/books/{isbn}`: Path variable overrides ISBN, prevents mismatch
- Nested author object in request payload
- Validates existing author ID (logic absent, needs implementation)
- HTTP 201 Created status

#### JPA Hibernate DDL Auto Setting

Instructs Hibernate to automatically update the database schema to match the current JPA entity models on application
startup.
This is convenient for development, but for production environments, consider using `validate` or managing schema
changes manually.

```editorconfig
    spring.jpa.hibernate.ddl-auto = update
```

### Testing Strategy

#### Integration Tests

**Features:**

- Loads full Spring context (`@SpringBootTest`)
- Resets context after each test method (`@DirtiesContext`)
- Uses MockMvc for HTTP endpoint testing without server startup (`@AutoConfigureMockMvc`)
- H2 database isolates tests from development DB

**AuthorControllerIntegrationTests:**

- Tests author creation endpoint
- Verifies JSON serialization, HTTP status, field mapping
- Demonstrates ObjectMapper injected from MapperConfig

**BookControllerIntegrationTests:**

- Tests book creation with nested author
- Uses PUT method with path variable
- Validates business logic (if implemented)

#### Repository Integration Tests

**Comprehensive CRUD Coverage:**

- Create, Read (single/multiple), Update, Delete operations
- Custom query testing: Derived methods, JPQL, native SQL
- Relationship testing through cascading saves

### API Examples

#### Authors

| Method | Endpoint                        |
|--------|---------------------------------|
| POST   | /authors                        |
| GET    | /list-authors                   |
| GET    | /page-authors                   |
| GET    | /authors                        |
| GET    | /authors/{id}                   |
| GET    | /authors/age-less-than/{age}    |
| GET    | /authors/age-greater-than/{age} |
| GET    | /authors/search?name=           |
| PUT    | /authors/update/{id}            |
| PATCH  | /authors/patch/{id}             |
| DELETE | /authors/{id}                   |

#### Books

| Method | Endpoint       |
|--------|----------------|
| PUT    | /books/{isbn}  |
| GET    | /books         |
| GET    | /books/{isbn}  |
| PATCH  | /books/{isbn}  |
| DELETE | /books/{isbn}  |

#### API Requests data

**Authors API Endpoints:**

- Author creation
    - HTTP method `POST`, path `/authors`
    - Create a new author
    - `id` is auto-generated
    - Request: AuthorDto (name, age)
    - Response: 201 Created with full AuthorDto including generated id

    ```json
        {
            "name": "Sanjeev Sanyal",
            "age": 50
        }
    ```

- List all authors
    - HTTP method `GET`, path `/list-authors`
    - Retrieve all authors as list
    - No request body required

- Paginated authors list
    - HTTP method `GET`, path `/page-authors?page=0&size=10`
    - Retrieve paginated authors
    - Query params: page (default 0), size (default 20)
    - No request body required

- Get authors list (alternative endpoint)
    - HTTP method `GET`, path `/authors`
    - Retrieve all authors as list
    - No request body required

- Get author by ID
    - HTTP method `GET`, path `/authors/{id}`
    - Retrieve specific author by ID
    - Path variable: id (Long, required)
    - No request body required

- Find authors by age less than
    - HTTP method `GET`, path `/authors/age-less-than/{age}`
    - Retrieve authors younger than specified age
    - Path variable: age (Integer, required)
    - No request body required

- Find authors by age greater than
    - HTTP method `GET`, path `/authors/age-greater-than/{age}`
    - Retrieve authors older than specified age
    - Path variable: age (Integer, required)
    - No request body required

- Search authors by name
    - HTTP method `GET`, path `/authors/search?name={name}`
    - Find authors by name (partial match)
    - Query param: name (String, required, non-empty)
    - No request body required

- Update author (full update)
    - HTTP method `PUT`, path `/authors/update/{id}`
    - Replace entire author record
    - Path variable: id (Long, required, must exist)
    - Request: AuthorDto (id auto-set to path variable)

    ```json
        {
            "name": "Updated Name",
            "age": 55
        }
    ```

- Partial update author
    - HTTP method `PATCH`, path `/authors/patch/{id}`
    - Update only provided fields
    - Path variable: id (Long, required, must exist)
    - Request: AuthorDto (only fields to update, null fields ignored)

    ```json
        {
            "age": 60
        }
    ```

- Delete author by ID
    - HTTP method `DELETE`, path `/authors/{id}`
    - Remove author from database
    - Path variable: id (Long, required, must exist)
    - No request or response body

**Books API Endpoints:**

- Create/Update book (upsert)
    - HTTP method `PUT`, path `/books/{isbn}`
    - Create new book or update existing one by ISBN
    - Path variable: isbn (String, required, overrides request body ISBN)
    - Request: BookDto with nested AuthorDto (includes author id/nested author data)
    - Enables cascading author creation/saves
    - Author relationship updates trigger cascading saves 

    ```json
        {
          "isbn": "9780-lkw8-4789",
          "title": "Land of seven rivers-2",
          "authorDto": {
            "id": 1,
            "name": "Sanjeev Sanyal",
            "age": 50
          }
        }
    ```

- List all books
    - HTTP method `GET`, path `/books`
    - Retrieve all books with nested author details
    - No request body required

- Get book by ISBN
    - HTTP method `GET`, path `/books/{isbn}`
    - Retrieve specific book by ISBN
    - Path variable: isbn (String, required)
    - No request body required

- Partial update book
    - HTTP method `PATCH`, path `/books/{isbn}`
    - Update only provided book fields
    - Path variable: isbn (String, required, must exist)
    - Request: BookDto (only fields to update, null/Ignored fields)

    ```json
        {
          "title": "Updated Title"
        }
    ```

- Delete book by ISBN
    - HTTP method `DELETE`, path `/books/{isbn}`
    - Remove book from database
    - Path variable: isbn (String, required, must exist)
    - No request or response body (cascading delete not applied)

### Queries Summary

```sql
    SELECT * FROM authors
    ORDER BY id ASC;
    
    SELECT * FROM books
    ORDER BY isbn ASC;
    
    SELECT * FROM authors AS a
    INNER JOIN books AS b
    ON a.id = b.author_id;
    
    TRUNCATE TABLE books;
```
