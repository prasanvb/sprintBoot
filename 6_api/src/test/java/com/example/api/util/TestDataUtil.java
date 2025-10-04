package com.example.api.util;

import com.example.api.domain.entity.AuthorEntity;
import com.example.api.domain.entity.BookEntity;

public class TestDataUtil {

    // Private constructor to prevent instantiation
    private TestDataUtil() {
    }

    public static AuthorEntity buildAuthor(Long id, String name, int age) {
        return AuthorEntity.builder()
                .id(id)
                .name(name)
                .age(age)
                .build();
    }

    public static BookEntity buildBook(String isbn, String title, AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn(isbn)
                .title(title)
                .authorEntityId(authorEntity)
                .build();
    }
}
