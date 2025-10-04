# Spring Boot JPA

### Hibernate auto DDL

```editorconfig
    spring.jpa.hibernate.ddl-auto=update
```

### Entity Relationship Diagram

```mermaid
    erDiagram
        books {
            text isbn
            text title
            num author_id
        }
    
        author {
            num id
            text name
            num age
        }
    
        books }o--|| author : "written_by"
```

### Spring Data JPA 

- Without any implementation 

    ```java
    Iterable<Author> ageLessThan(int age);
    ```
  
- Custom methods and queries - [AuthorRepository](src/main/java/com/example/db/repositories/AuthorRepository.java)
