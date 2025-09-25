# Java DataSource Study Notes

## What is a DataSource?

- **DataSource** is a Java interface (`javax.sql.DataSource`) used for getting connections to a database.
- It is the modern alternative to the older `DriverManager` class.
- Commonly used in enterprise Java applications and frameworks like Spring.

---

## Why Use DataSource?

- **Connection Pooling:** Efficiently manages a pool of database connections for reuse, improving performance.
- **Configuration:** Database details (URL, username, password, pool size, etc.) are managed externally (e.g., in properties files).
- **Integration:** Easily integrates with frameworks and containers (like Spring Boot or Java EE).
- **Resource Management:** Handles connection opening and closing, reducing errors and leaks.

---

## Typical Workflow

1. **Configure DataSource**  
   Set connection details in a config file (e.g., `application.properties` in Spring Boot).
    ```
    spring.datasource.url=jdbc:postgresql://0.0.0.0:5433/postgres
    spring.datasource.username=postgres
    spring.datasource.password=postgres
    spring.datasource.driver-class-name=org.postgresql.Driver

    // data initialization
    spring.sql.init.mode=always
    ```

2. **Inject DataSource**  
   Use dependency injection to get a DataSource object in your code.

   ```java
   private final DataSource dataSource;

   public YourClass(DataSource dataSource) {
       this.dataSource = dataSource;
   }
   ```

   Dependency injection using Beans
    ```java
    @Configuration
    public class DatabaseConfig {
    
        @Bean
        public JdbcTemplate jdbcTemplate(final DataSource dataSource){
            return new JdbcTemplate(dataSource);
        }
    }
    ```

3. **Get a Connection** 
   Use `dataSource.getConnection()` to get a database connection.

---

## DataSource vs DriverManager

| Feature            | DriverManager | DataSource   |
|--------------------|---------------|--------------|
| Connection Pool    | No            | Yes (often)  |
| Managed by Server  | No            | Yes (can be) |
| Config Flexibility | Low           | High         |
| Recommended?       | No            | Yes          |
