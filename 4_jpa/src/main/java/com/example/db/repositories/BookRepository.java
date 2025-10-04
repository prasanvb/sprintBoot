package com.example.db.repositories;

import com.example.db.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// CurdRepository is a generic interface that takes two parameters: the entity type and the type of its primary key.
// The Book entity is assumed to have a primary key of type String.
@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
