package com.example.api.services;

import com.example.api.domain.entity.BookEntity;

import java.util.List;

public interface BookService {
    BookEntity createBook(BookEntity bookEntity);

    List<BookEntity> findAll();
}
