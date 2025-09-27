package com.example.jdbc.dao.dao;

import com.example.jdbc.dao.dao.impl.BookDaoImpl;
import com.example.jdbc.dao.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    public void verifyBookDaoGeneratesCorrectSql(){
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
}
