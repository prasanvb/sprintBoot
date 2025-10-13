package com.example.api.path;

public class BookPaths {
    public static final String BOOK_BY_ISBN = "/books/{isbn}";
    public static final String BOOKS = "/books";

    public static String bookByIsbnUrl(String isbn) {
        return BOOK_BY_ISBN.replace("{isbn}", isbn);
    }
}
