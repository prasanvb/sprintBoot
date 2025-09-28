package com.example.jdbc.dao.util;

import com.example.jdbc.dao.domain.Author;
import com.example.jdbc.dao.domain.Book;

public class TestDataUtil {

    // Private constructor to prevent instantiation
    private TestDataUtil() {
    }

    public static Author buildAuthor(Long id, String name, int age) {
        return Author.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
    }

    public static Book buildBook(String isbn, String title, Long authorId) {
        return Book.builder()
                .isbn(isbn)
                .title(title)
                .authorId(authorId)
                .build();
    }
}
