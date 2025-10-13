package com.example.api.repositories;

import com.example.api.ApiApplication;
import com.example.api.domain.entity.AuthorEntity;
import com.example.api.domain.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static com.example.api.util.Constants.*;
import static com.example.api.util.TestDataUtil.buildAuthor;
import static com.example.api.util.TestDataUtil.buildBook;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ApiApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookEntityRepositoryIntegrationTest {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookEntityRepositoryIntegrationTest(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndQueryed() {
        // NOTE: If DirtiesContext not used the ID should be incremented by 1 to prevent test failure
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);


        BookEntity bookEntity = buildBook(ISBN, TITLE, savedAuthorEntity);
        bookRepository.save(bookEntity);
        Optional<BookEntity> result = bookRepository.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatNoBookCreatedAndQueryed() {
        Iterable<BookEntity> result = bookRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatManyBookCanBeCreatedAndQueryed() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);

        BookEntity bookEntity = buildBook(ISBN, TITLE, savedAuthorEntity);
        BookEntity book_Entity_2 = buildBook(ISBN_2, TITLE_2, authorEntity);

        Iterable<BookEntity> booksList = List.of(bookEntity, book_Entity_2);
        Iterable<BookEntity> result = bookRepository.saveAll(booksList);

        assertThat(result).hasSize(2).containsExactly(bookEntity, book_Entity_2);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        authorRepository.save(authorEntity);

        BookEntity bookEntity = buildBook(ISBN, TITLE, authorEntity);
        bookRepository.save(bookEntity);

        System.out.println("Original bookEntity values: " + bookEntity);

        bookEntity.setTitle(TITLE_2);
        bookRepository.save(bookEntity);

        Optional<BookEntity> result = bookRepository.findById(bookEntity.getIsbn());

        assertThat(result).isPresent().get().isEqualTo(bookEntity);
        System.out.println("Updated bookEntity values: " + result.get());
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        authorRepository.save(authorEntity);

        BookEntity bookEntity = buildBook(ISBN, TITLE, authorEntity);
        bookRepository.save(bookEntity);

        bookRepository.deleteById(bookEntity.getIsbn());

        Optional<BookEntity> result = bookRepository.findById(bookEntity.getIsbn());

        assertThat(result).isEmpty();
    }

}
