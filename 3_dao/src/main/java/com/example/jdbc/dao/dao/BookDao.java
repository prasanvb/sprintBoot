package com.example.jdbc.dao.dao;

import com.example.jdbc.dao.domain.Book;

import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);
}
