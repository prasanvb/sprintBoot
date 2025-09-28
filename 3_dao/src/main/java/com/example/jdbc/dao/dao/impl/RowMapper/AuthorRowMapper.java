package com.example.jdbc.dao.dao.impl.RowMapper;

import com.example.jdbc.dao.domain.Author;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AuthorRowMapper implements RowMapper<Author> {
    // Static inner class that implements RowMapper to convert database result set rows into Author domain objects
    // RowMapper is used by JdbcTemplate.query() to transform each SQL result row into a Java object

    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Resultset Metadata and print colum headers
//            System.out.println("Author mapRow - Result Set Meta data");
//            ResultSetMetaData meta = rs.getMetaData();
//            for (int i = 1; i <= meta.getColumnCount(); i++) {
//                System.out.print(meta.getColumnName(i) + "\n");
//            }

        // Map the current row of the result set to an Author instance using the builder pattern
        // Extract the column value as a String from the respective columns and assigned to the corresponding Author field
        // Build and return the Book object with these values
        return Author.builder()
                .id(rs.getLong("id"))     // Maps the 'id' column (Long) to the Author's id field
                .name(rs.getString("name"))  // Maps the 'name' column (String) to the Author's name field
                .age(rs.getInt("age"))    // Maps the 'age' column (Integer) to the Author's age field
                .build();  // Builds and returns the complete Author object
    }
}