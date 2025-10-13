package com.example.api.services.impl;

import com.example.api.domain.entity.BookEntity;
import com.example.api.repositories.BookRepository;
import com.example.api.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
     *
     * @param bookEntity the book entity to create
     * @return the saved book entity
     */
    @Override
    public BookEntity saveBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        Iterable<BookEntity> books = bookRepository.findAll();
        // stream method from StreamSupport class converts the iterable result to a new sequential or parallel stream from a Spliterator.
        // collect method from Stream class accumulates the elements of this stream into a List. The elements in the list will be in this stream's encounter order, if one exists.
        // Collector accumulates the input elements into a new List. There are no guarantees on the type, mutability, serializability, or thread-safety of the List.
        return StreamSupport
                .stream(books.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookEntity> findById(String isbn) {
        return bookRepository.findById(isbn);

    }

    @Override
    public Boolean isBookExists(String isbn) {
        return bookRepository.existsById(isbn);
    }
}
