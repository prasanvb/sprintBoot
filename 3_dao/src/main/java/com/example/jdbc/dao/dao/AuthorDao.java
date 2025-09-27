package com.example.jdbc.dao.dao;

import com.example.jdbc.dao.domain.Author;

import java.util.Optional;

public interface AuthorDao {
    void create(Author author);

    Optional<Author> findOne(Long authorId);
}
