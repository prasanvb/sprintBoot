package com.spring.json.controllers;

import com.spring.json.domain.Book;
import com.spring.json.utils.Utils;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class BookController {
    @GetMapping(path = "/books")
    public Book retrieveBook() {

        // NOTE: we are returning a Java object here. Spring Boot will use Jackson
        // to convert this Java object to JSON and send it back to the client.
        return Utils.getBookJavaObject();
    }

    @PostMapping(path = "/books")
    public Book createBook(@NotNull @RequestBody final Book book) {
        log.info("Create book: " + book.toString());
        // NOTE: we are accepting a Java object here. Spring Boot will use Jackson
        // to convert the incoming JSON to a Java object.
        return book;
    }
}