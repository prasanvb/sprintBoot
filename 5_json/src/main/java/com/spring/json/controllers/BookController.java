package com.spring.json.controllers;

import com.spring.json.domain.Book;
import com.spring.json.utils.Utils;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller demonstrating Jackson's automatic JSON-Java conversion in Spring MVC.
 * Spring Boot auto-configures MappingJackson2HttpMessageConverter for seamless serialization.
 */
@RestController // Spring: Combines @Controller + @ResponseBody; all methods return JSON responses
@Log // Lombok: Adds logging field 'log' using java.util.logging
public class BookController {

    /**
     * GET endpoint demonstrating Jackson serialization (Java -> JSON).
     * Spring MVC automatically converts returned POJO to JSON using Jackson.
     */
    @GetMapping(path = "/books") // Maps HTTP GET requests to this method
    public Book retrieveBook() {
        // NOTE: we are returning a Java object here. Spring Boot will use Jackson
        // to convert this Java object to JSON and send it back to the client.
        return Utils.getBookJavaObject();
    }

    /**
     * POST endpoint demonstrating Jackson deserialization (JSON -> Java).
     * Spring MVC converts incoming JSON request body to Java object using Jackson.
     */
    @PostMapping(path = "/books") // Maps HTTP POST requests to this method
    public Book createBook(@NotNull @RequestBody final Book book) { // @RequestBody: Jackson deserializes JSON to Book
        log.info("Create book: " + book.toString()); // Logs the deserialized book object
        // NOTE: we are accepting a Java object here. Spring Boot will use Jackson
        // to convert the incoming JSON to a Java object.
        return book; // Returns the same object - Spring auto-serializes back to JSON
    }
}
