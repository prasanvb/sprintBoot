package com.example.jdbc.dao.dao;

import com.example.jdbc.dao.dao.impl.AuthorDaoImpl;
import com.example.jdbc.dao.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthorDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl authorDaoImpl;

    @Test
    public void createAuthorDaoGeneratesCorrectSql(){
        Author author = Author.builder()
                            .id(1L)
                            .name("APJ Kalam")
                            .age(100)
                            .build();

        authorDaoImpl.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?,?,?)"), eq(1L), eq("APJ Kalam"), eq(100)
        );
    }

}
