package com.example.api.services;

import com.example.api.domain.entity.BookEntity;

public interface BookService {
    BookEntity createBook(BookEntity bookEntity);
}
