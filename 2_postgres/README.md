# Spring Boot PostgreSQL JDBC Connection Example

This project demonstrates how to connect a Spring Boot application to a PostgreSQL database running in Docker, with automatic JDBC connection testing on startup.

## Prerequisites

- Docker and Docker Compose
- Java 17+ for running the Spring Boot application
- PostgreSQL client (optional, for manual database verification)

## Quick Start

### 1. Start PostgreSQL Database
```bash
# Navigate to project directory and start the database
docker compose up -d

# View running containers
docker ps

# Stop containers when done
docker compose down
```

### 2. Run Spring Boot Application
```bash
# Run via Maven
mvn spring-boot:run

# Or run via IDE
# Check console logs for successful connection confirmation
```

The application automatically tests the JDBC connection on startup and logs the result.

## Project Structure

- **[PostgresApplication.java](src/main/java/com/example/jdbc/postgres/PostgresApplication.java)**: Main application class with JDBC connection testing
- **[application.properties](src/main/resources/application.properties)**: Database connection configuration
- **[schema.sql](src/main/resources/schema.sql)**: Database schema definition (widgets table)
- **[data.sql](src/main/resources/data.sql)**: Sample data for testing

## Configuration

### Database Properties
```properties
# Connection settings
spring.datasource.url=jdbc:postgresql://localhost:5433/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver

# Schema and data initialization
spring.sql.init.mode=always
```

### Key Configuration Notes
- Database runs in Docker container (port 5432) mapped to host port 5433
- Schema and data scripts execute automatically on each application startup
- Uses `0.0.0.0` in URL for proper container networking

## Understanding DataSource

**DataSource** (`javax.sql.DataSource`) is the standard Java interface for database connections, offering significant advantages over the older `DriverManager`:

### Benefits
- **Connection Pooling:** Manages reusable connection pools for better performance
- **External Configuration:** Database settings managed in properties files
- **Framework Integration:** Seamless integration with Spring Boot and Java EE
- **Resource Management:** Automatic connection lifecycle management

### Usage Pattern
```java
// 1. Inject DataSource via constructor or @Autowired
private final DataSource dataSource;

public YourService(DataSource dataSource) {
    this.dataSource = dataSource;
}

// 2. Obtain connections as needed
try (Connection conn = dataSource.getConnection()) {
    // Execute queries
    String query = "SELECT * FROM widgets";
    try (PreparedStatement stmt = conn.prepareStatement(query)) {
        // Process results
    }
}
```

### DataSource vs DriverManager

| Feature                | DriverManager       | DataSource            |
|------------------------|---------------------|-----------------------|
| **Connection Pooling** | ❌ Manual management | ✅ Built-in support    |
| **Server Management**  | ❌ Not supported     | ✅ JNDI integration    |
| **Configuration**      | ❌ Hardcoded usually | ✅ External properties |
| **Recommended**        | ❌ Legacy approach   | ✅ Modern standard     |

## Manual Database Verification (Optional)

Connect using any Postgres SQL client:

The `widgets` table will be automatically created and populated with sample data.
