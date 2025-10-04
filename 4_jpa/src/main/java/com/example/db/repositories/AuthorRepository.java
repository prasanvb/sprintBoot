package com.example.db.repositories;

import com.example.db.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Note that the interface extends CrudRepository, which provides basic CRUD operations.
// CurdRepository is a generic interface that takes two parameters: the entity type and the type of its primary key.
// The Author entity is assumed to have a primary key of type Long.
@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

    // Spring Data JPA can automatically derive queries from method names without requiring explicit implementation.
    // This feature relies on clear, understandable method names and doesn't support all possible query scenarios.
    Iterable<Author> ageLessThan(int age);

    // Custom Hibernate Query Language
    @Query(value = "SELECT a from Author a where a.age > ?1")
    Iterable<Author> findAuthorsAgeGreaterThan(int i);

    // Custom SQL query
    @Query(value = "SELECT * FROM authors WHERE name = ?", nativeQuery = true)
    Iterable<Author> findAuthorByName(String name);
}
