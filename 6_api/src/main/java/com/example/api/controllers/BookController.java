package com.example.api.controllers;

import com.example.api.domain.dto.BookDto;
import com.example.api.domain.entity.BookEntity;
import com.example.api.mappers.Mapper;
import com.example.api.services.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.api.path.BookPaths.BOOKS;
import static com.example.api.path.BookPaths.BOOK_BY_ISBN;


@RestController
public class BookController {
    private final Mapper<BookEntity, BookDto> bookMapper;
    private final BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }


    @PutMapping(path = BOOK_BY_ISBN)
    public ResponseEntity<BookDto> createUpdateBookByIsbn(@PathVariable String isbn, @RequestBody BookDto bookDto) {

        if (isbn == null || isbn.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Overwrites the ISBN from the path variable to ensure consistency
        bookDto.setIsbn(isbn);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);

        boolean bookExisted = bookService.isBookExists(isbn);

        BookEntity savedBookEntity = bookService.saveBook(bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);

        if (bookExisted) {
            return new ResponseEntity<>(savedBookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);
        }


    }

    @GetMapping(path = BOOKS)
    public List<BookDto> listBooks() {
        List<BookEntity> bookEntities = bookService.findAll();
        return bookEntities
                .stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = BOOK_BY_ISBN)
    public ResponseEntity<BookDto> findBookByIsbn(@PathVariable String isbn) {

        if (isbn == null || isbn.trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<BookEntity> identifiedBookByIsbn = bookService.findById(isbn);

        return identifiedBookByIsbn.map(bookEntity -> {
            BookDto foundBookEntity = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(foundBookEntity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
