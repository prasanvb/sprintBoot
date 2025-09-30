# Postgres SQL Database Connection with Spring Boot

This project demonstrates how to run a PostgreSQL database in a Docker container and establish a JDBC connection from a Spring Boot application to test the connection.

## Prerequisites

- Docker and Docker Compose installed
- Java 17+ for running the Spring Boot application
- A Postgres SQL client like pgAdmin to verify the database connection

## Setup and Steps

1. **Start the Postgres SQL Database Container**
   - Navigate to the project directory in your terminal.
   - Run `docker compose up` to start the PostgreSQL container.
   - The database will be accessible on `0.0.0.0:5433` (mapped from container port 5432).
   - To list running containers: `docker ps`
   - To stop the containers: `docker compose down`

2. **Verify Database Connection**
   - Use a PostgreSQL client like pgAdmin to connect to the database:
     - **Host:** localhost
     - **Port:** 5433
     - **Database:** postgres
     - **Username:** postgres
     - **Password:** postgres
   - You should see the `widgets` table initialized with sample data after connecting.

3. **Test JDBC Connection from Spring Boot App**
   - The application automatically tests the connection on startup by executing a simple query.
   - Run the Spring Boot application (e.g., via IDE or `mvn spring-boot:run`).
   - Check the console logs for the DataSource info and successful query execution.

## Project Structure

- **[PostgresApplication.java](src/main/java/com/example/jdbc/postgres/PostgresApplication.java)**: Main application class that implements JDBC connection testing.
- **[application.properties](src/main/resources/application.properties)**: Contains database connection settings.
- **[schema.sql](src/main/resources/schema.sql)**: Defines the database schema (e.g., `widgets` table).
- **[data.sql](src/main/resources/data.sql)**: Inserts sample data into the `widgets` table for testing.

## Configuration Notes

- The database initialization mode is set to `always`, so schema and data scripts run on each startup.
- Connection URL: `jdbc:postgresql://0.0.0.0:5433/postgres` (0.0.0.0 is used for local connectivity in this setup).
