package com.example.api.repositories;

import com.example.api.domain.entity.AuthorEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<AuthorEntity, Long> {

    // Derived query: SELECT * FROM authors WHERE age < ?
    Iterable<AuthorEntity> ageLessThan(int age);

    // JPQL query using entity names and parameters
    @Query(value = "SELECT a from AuthorEntity a where a.age > ?1")
    Iterable<AuthorEntity> findAuthorsAgeGreaterThan(int i);

    // Native SQL query against table columns
    @Query(value = "SELECT * FROM authors WHERE name = ?", nativeQuery = true)
    Iterable<AuthorEntity> findAuthorByName(String name);
}
