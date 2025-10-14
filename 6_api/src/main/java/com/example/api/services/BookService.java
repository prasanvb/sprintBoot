package com.example.api.services;

import com.example.api.domain.entity.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    BookEntity saveBook(BookEntity bookEntity);

    List<BookEntity> findAll();

    Optional<BookEntity> findById(String isbn);

    Boolean isBookExists(String isbn);

    BookEntity partialUpdateBook(String isbn, BookEntity bookEntity);

    void deleteBookByIsbn(String isbn);
}
