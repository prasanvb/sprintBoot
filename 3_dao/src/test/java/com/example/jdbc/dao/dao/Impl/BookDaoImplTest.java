package com.example.jdbc.dao.dao.Impl;

import com.example.jdbc.dao.dao.impl.BookDaoImpl;
import com.example.jdbc.dao.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

// Enables Mockito integration with JUnit 5 for automated mock management
@ExtendWith(MockitoExtension.class)
public class BookDaoImplTest {

    // Mock object for JdbcTemplate to simulate database operations in tests
    @Mock
    private JdbcTemplate jdbcTemplate;

    // Injects mock dependencies into the class under test (AuthorDaoImpl)
    @InjectMocks
    private BookDaoImpl bookDaoImpl;

    // Marks this method as a JUnit test case
    @Test
    public void verifyCreateBookDaoGeneratesCorrectSql(){
        Book book = Book.builder()
                .isbn("ae1")
                .title("Wings of Fire")
                .authorId(1L).build();

        // Execute the create method on the DAO implementation being tested
        bookDaoImpl.create(book);

        // Verify that the JdbcTemplate.update was called with the expected SQL insert statement and parameters
        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) values (?,?,?)"),
                eq("ae1"),
                eq("Wings of Fire"),
                eq(1L)
        );
    }

    @Test
    public void verifyFindOneAuthorDaoGeneratesCorrectSql(){
        // Call the findOne method with the ISBN "ae1" to retrieve a single book
        bookDaoImpl.findOne("ae1");

        // Verify that JdbcTemplate.query is called with the expected SQL query,
        // which selects isbn, title, and author_id from the books table where the isbn matches,
        // and limits the result to 1 record
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                // Use any instance of BookDaoImpl.BookRowMapper, a custom row mapper class
                // that maps the database result set rows to Book domain objects
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq("ae1")
        );
    }
}
