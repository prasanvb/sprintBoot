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

import static com.example.jdbc.dao.util.Constants.*;
import static com.example.jdbc.dao.util.TestDataUtil.buildBook;
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
    public void verifyCreateBookMethodInDaoGeneratesCorrectSql(){
        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);

        // Execute the create method on the DAO implementation being tested
        bookDaoImpl.create(book);

        // Verify that the JdbcTemplate.update was called with the expected SQL insert statement and parameters
        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) values (?,?,?)"),
                eq(ISBN),
                eq(TITLE),
                eq(AUTHOR_ID)
        );
    }

    @Test
    public void verifyFindOneBookMethodInDaoGeneratesCorrectSql(){
        // Call the findOne method with the ISBN to retrieve a single book
        bookDaoImpl.findOne(ISBN);

        // Verify that JdbcTemplate.query is called with the expected SQL query,
        // which selects isbn, title, and author_id from the books table where the isbn matches,
        // and limits the result to 1 record
        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ? LIMIT 1"),
                // Use any instance of BookDaoImpl.BookRowMapper, a custom row mapper class
                // that maps the database result set rows to Book domain objects
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any(),
                eq(ISBN)
        );
    }

    @Test
    public void verifyFindManyBooMethodInDaoGeneratesCorrectSql(){
        bookDaoImpl.find();

        verify(jdbcTemplate).query(eq("SELECT isbn, title, author_id FROM books"),
                ArgumentMatchers.<BookDaoImpl.BookRowMapper>any());
    }

    @Test
    public void verifyUpdateBookDaoMethodInGeneratesCorrectSql(){
        Book book = buildBook(ISBN_2, TITLE_2, AUTHOR_ID);

        bookDaoImpl.update(book, ISBN_2);

        // Verify that the JdbcTemplate.update was called with the expected SQL insert statement and parameters
        verify(jdbcTemplate).update(
                eq("UPDATE books SET isbn = ? , title = ? , author_id = ? WHERE isbn = ?"),
                eq(ISBN_2),
                eq(TITLE_2),
                eq(AUTHOR_ID),
                eq(ISBN_2)
        );
    }

    @Test
    public void verifyDeleteBookDaoMethodInGeneratesCorrectSql(){
        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);
        bookDaoImpl.create(book);

        bookDaoImpl.delete(ISBN);

        // Verify that the JdbcTemplate.update was called with the expected SQL insert statement and parameters
        verify(jdbcTemplate).update(eq("DELETE FROM books WHERE isbn = ?"), eq(ISBN));
    }
}
