package com.example.api.repositories;

import com.example.api.domain.entity.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    // Spring Data JPA can automatically derive queries from method names without requiring explicit implementation.
    // This feature relies on clear, understandable method names and doesn't support all possible query scenarios.
    Iterable<AuthorEntity> ageLessThan(int age);

    // Custom Hibernate Query Language
    @Query(value = "SELECT a from AuthorEntity a where a.age > ?1")
    Iterable<AuthorEntity> findAuthorsAgeGreaterThan(int i);

    // Custom SQL query
    @Query(value = "SELECT * FROM authors WHERE name = ?", nativeQuery = true)
    Iterable<AuthorEntity> findAuthorByName(String name);
}
