package com.example.jdbc.dao.dao.Impl;

import com.example.jdbc.dao.dao.impl.AuthorDaoImpl;
import com.example.jdbc.dao.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static com.example.jdbc.dao.util.Constants.*;
import static com.example.jdbc.dao.util.TestDataUtil.buildAuthor;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

// Enables Mockito integration with JUnit 5 for automated mock management
@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTest {

    // Mock object for JdbcTemplate to simulate database operations in tests
    @Mock
    private JdbcTemplate jdbcTemplate;

    // Injects mock dependencies into the class under test (AuthorDaoImpl)
    @InjectMocks
    private AuthorDaoImpl authorDaoImpl;

    // Marks this method as a JUnit test case
    @Test
    public void verifyCreateAuthorDaoGeneratesCorrectSql(){
        // Create a test Author object using the Builder pattern with sample data
        Author author = buildAuthor(ID, NAME, AGE);

        // Execute the create method on the DAO implementation being tested
        authorDaoImpl.create(author);

        // Verify that the JdbcTemplate.update was called with the expected SQL insert statement and parameters
        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?,?,?)"),
                eq(ID),
                eq(NAME),
                eq(AGE)
        );
    }

    @Test
    public void verifyFindOneAuthorDaoGeneratesCorrectSql(){
        // Execute the findOne method with a test ID (1L) to trigger database interaction
        authorDaoImpl.findOne(ID);

        // Verify that JdbcTemplate.query was called exactly once with the expected parameters:
        // - ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(): Matches any instance of AuthorRowMapper (since it's created fresh each time)
        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),  // Accepts any AuthorRowMapper instance
                eq(ID)
        );

    }
}
