package com.example.api.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // Marks this class as a JPA entity, representing a database table
@Table(name = "books") // Specifies the table name in the database
public class BookEntity {

    // Marks this field as the primary key of the entity
    @Id
    private String isbn;

    // Represents the book's title column in the database
    private String title;

    // Defines a many-to-one relationship with AuthorEntity
    // CascadeType.ALL propagates all operations to the associated author
    @ManyToOne(cascade = CascadeType.ALL)
    // Specifies the foreign key column name in the books table
    @JoinColumn(name = "author_id")
    private AuthorEntity authorEntity;

}
