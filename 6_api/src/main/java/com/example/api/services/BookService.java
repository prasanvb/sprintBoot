package com.example.api.services;

import com.example.api.domain.entity.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity createBook(BookEntity bookEntity);

    List<BookEntity> findAll();

    Optional<BookEntity> findById(String isbn);
}
