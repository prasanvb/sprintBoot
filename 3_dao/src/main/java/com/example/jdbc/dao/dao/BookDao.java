package com.example.jdbc.dao.dao;

import com.example.jdbc.dao.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String isbn);

    List<Book> find();

    void update(Book book, String isbn);
}
