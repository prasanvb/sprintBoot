package com.example.jdbc.dao.dao.impl;

import com.example.jdbc.dao.dao.BookDao;
import com.example.jdbc.dao.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;

public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO books (isbn, title, author_id) values (?,?,?)",
                book.getIsbn(), book.getTitle(), book.getAuthorId());
    }
}
