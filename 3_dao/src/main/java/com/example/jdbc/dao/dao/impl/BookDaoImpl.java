package com.example.jdbc.dao.dao.impl;

import com.example.jdbc.dao.dao.BookDao;
import com.example.jdbc.dao.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Book> findOne(String isbn) {
        List<Book> findOneBookQueryResult = jdbcTemplate.query(
                "SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1",
                // Use a new instance of BookRowMapper to convert the result set rows into Book objects
                new BookRowMapper(),
                isbn
        );

        // Convert the list of results to an Optional, returning the first book if present,
        // or an empty Optional if no book was found
        return findOneBookQueryResult.stream().findFirst();
    }

    // Static inner class that implements RowMapper to convert a database result set row into a Book object
    public static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Map the current row of the result set to a Book instance using the builder pattern
            // Extract the isbn as a String from the "isbn" column
            // Extract the title as a String from the "title" column
            // Extract the author_id as a Long from the "author_id" column
            // Build and return the Book object with these values
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id")).build();
        }
    }
}
