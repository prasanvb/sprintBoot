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

    /**
     * Creates and saves a new book entity.
     * This method is implicitly transactional - Spring Data JPA transactions handle persistence.
     * If the book's AuthorEntity has CascadeType.ALL, saving the book may also persist/update the author.
     * The ISBN serves as the natural primary key (no auto-generation).
     * @param bookEntity the book entity to create
     * @return the saved book entity
     */
    @Override
    public BookEntity createBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }
}
