package com.example.db.util;

import com.example.db.domain.Author;
import com.example.db.domain.Book;

public class TestDataUtil {

    // Private constructor to prevent instantiation
    private TestDataUtil() {
    }

    public static Author buildAuthor(Long id, String name, int age) {
        // Using builder pattern to create Author entity instance
        return Author.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
    }

    public static Book buildBook(String isbn, String title, Author author) {
        // Using builder pattern to create Book entity instance
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .authorId(author)
                .build();
    }
}
