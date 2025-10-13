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


@RestController
public class BookController {
    private final Mapper<BookEntity, BookDto> bookMapper;
    private final BookService bookService;

    public BookController(Mapper<BookEntity, BookDto> bookMapper, BookService bookService) {
        this.bookMapper = bookMapper;
        this.bookService = bookService;
    }


    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        // Overwrites the ISBN from the path variable to ensure consistency
        bookDto.setIsbn(isbn);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.createBook(bookEntity);
        BookDto savedBookDto = bookMapper.mapTo(savedBookEntity);
        return new ResponseEntity<>(savedBookDto, HttpStatus.CREATED);

    }

    @GetMapping(path = "/books")
    public List<BookDto> books() {
        List<BookEntity> bookEntities = bookService.findAll();
        return bookEntities
                .stream()
                .map(bookMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> findBookByIsbn(@PathVariable String isbn) {
        Optional<BookEntity> identifiedBookByIsbn = bookService.findById(isbn);

        return identifiedBookByIsbn.map(bookEntity -> {
            BookDto foundBookEntity = bookMapper.mapTo(bookEntity);
            return new ResponseEntity<>(foundBookEntity, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}