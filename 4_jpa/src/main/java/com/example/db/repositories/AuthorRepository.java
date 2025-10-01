package com.example.db.repositories;

import com.example.db.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
