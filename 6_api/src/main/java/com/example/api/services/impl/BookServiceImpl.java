package com.example.api.services.impl;

import com.example.api.domain.entity.BookEntity;
import com.example.api.repositories.BookRepository;
import com.example.api.services.BookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }
}
