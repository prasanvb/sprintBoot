package com.example.jdbc.dao.dao.impl;

import com.example.jdbc.dao.dao.AuthorDao;
import com.example.jdbc.dao.dao.impl.RowMapper.AuthorRowMapper;
import com.example.jdbc.dao.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorDaoImpl implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;
    private final AuthorRowMapper authorRowMapper;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate, AuthorRowMapper authorRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorRowMapper = authorRowMapper;
    }

    @Override
    public void create(Author author) {
        jdbcTemplate.update(
                "INSERT INTO authors (id, name, age) VALUES (?,?,?)",
                author.getId(), author.getName(), author.getAge());
    }

    @Override
    public Optional<Author> findOne(Long authorId) {
        // Query the database to retrieve an Author by their unique ID
        // Uses JDBC template to execute a parameterized query for safety and performance
        List<Author> findOneQueryResults = jdbcTemplate.query(
                "SELECT id, name, age FROM authors WHERE id = ? LIMIT 1",
                new AuthorRowMapper(),  // RowMapper instance to convert database rows to Author objects
                authorId
        );

        // Convert the list of results (which may contain 0 or 1 authors) to an Optional
        // Returns empty Optional if no author found, or the first (and only) author wrapped in Optional
        return findOneQueryResults.stream().findFirst();
    }

    @Override
    public List<Author> find() {
        return jdbcTemplate.query("SELECT id, name, age FROM authors", authorRowMapper);
    }

    @Override
    public void update(Author author, Long id){
        jdbcTemplate.update(
                "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?",
                author.getId(),
                author.getName(),
                author.getAge(),
                id
        );
    }

    @Override
    public void delete(Long id){
        jdbcTemplate.update(
                "DELETE FROM authors WHERE id = ?",
                id
        );
    }
}
