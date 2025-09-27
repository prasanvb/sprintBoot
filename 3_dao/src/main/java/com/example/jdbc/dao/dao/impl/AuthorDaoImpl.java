package com.example.jdbc.dao.dao.impl;

import com.example.jdbc.dao.dao.AuthorDao;
import com.example.jdbc.dao.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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

     public static class AuthorRowMapper implements RowMapper<Author>{
        // Static inner class that implements RowMapper to convert database result set rows into Author domain objects
        // RowMapper is used by JdbcTemplate.query() to transform each SQL result row into a Java object

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Maps a single row from the ResultSet to an Author instance using the builder pattern
            // Each column value is extracted by name and assigned to the corresponding Author field
            return Author.builder()
                    .id(rs.getLong("id"))     // Maps the 'id' column (Long) to the Author's id field
                    .name(rs.getString("name"))  // Maps the 'name' column (String) to the Author's name field
                    .age(rs.getInt("age"))    // Maps the 'age' column (Integer) to the Author's age field
                    .build();  // Builds and returns the complete Author object
        }
    }
}
