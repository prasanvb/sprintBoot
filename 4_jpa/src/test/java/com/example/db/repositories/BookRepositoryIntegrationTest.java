package com.example.db.repositories;

import com.example.db.domain.Author;
import com.example.db.domain.Book;
import com.example.jpa.JpaApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static com.example.db.util.Constants.*;
import static com.example.db.util.TestDataUtil.buildAuthor;
import static com.example.db.util.TestDataUtil.buildBook;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = JpaApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryIntegrationTest(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndQueryed() {
        // NOTE: If DirtiesContext not used the ID should be incremented by 1 to prevent test failure
        Author author = buildAuthor(null, NAME, AGE);
        Author savedAuthor = authorRepository.save(author);


        Book book = buildBook(ISBN, TITLE, savedAuthor);
        bookRepository.save(book);
        Optional<Book> result = bookRepository.findById(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

//    @Test
//    public void testThatNoBookCreatedAndQueryed() {
//        List<Book> result = bookDaoImpl.find();
//
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    public void testThatManyBookCanBeCreatedAndQueryed() {
//        Author author = buildAuthor(ID, NAME, AGE);
//        authDaoImpl.create(author);
//        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);
//        bookDaoImpl.create(book);
//
//        Book book_2 = buildBook(ISBN_2, TITLE_2, AUTHOR_ID);
//        bookDaoImpl.create(book_2);
//
//        List<Book> result = bookDaoImpl.find();
//
//        assertThat(result).hasSize(2);
//
//    }
//
//    @Test
//    public void testThatBookCanBeUpdated() {
//        Author author = buildAuthor(ID, NAME, AGE);
//        authDaoImpl.create(author);
//
//        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);
//        bookDaoImpl.create(book);
//
//        System.out.println(book);
//
//        book.setTitle(TITLE_2);
//        bookDaoImpl.update(book, ISBN);
//
//        Optional<Book> result = bookDaoImpl.findOne(book.getIsbn());
//
//        assertThat(result).isPresent().get().isEqualTo(book);
//        System.out.println(result.get());
//    }
//
//    @Test
//    public void testThatBookCanBeDeleted() {
//        Author author = buildAuthor(ID, NAME, AGE);
//        authDaoImpl.create(author);
//
//        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);
//        bookDaoImpl.create(book);
//
//        bookDaoImpl.delete(ISBN);
//
//        Optional<Book> result = bookDaoImpl.findOne(book.getIsbn());
//
//        assertThat(result).isEmpty();
//
//
//    }

}
