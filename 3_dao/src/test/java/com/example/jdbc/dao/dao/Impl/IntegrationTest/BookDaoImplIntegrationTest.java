package com.example.jdbc.dao.dao.Impl.IntegrationTest;

import com.example.jdbc.dao.dao.impl.AuthorDaoImpl;
import com.example.jdbc.dao.dao.impl.BookDaoImpl;
import com.example.jdbc.dao.domain.Author;
import com.example.jdbc.dao.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static com.example.jdbc.dao.util.Constants.*;
import static com.example.jdbc.dao.util.TestDataUtil.buildAuthor;
import static com.example.jdbc.dao.util.TestDataUtil.buildBook;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntegrationTest {
    private final AuthorDaoImpl authDaoImpl;
    private final BookDaoImpl bookDaoImpl;

    @Autowired
    public BookDaoImplIntegrationTest(AuthorDaoImpl authDaoImp, BookDaoImpl bookDaoImp) {
        this.authDaoImpl = authDaoImp;
        this.bookDaoImpl = bookDaoImp;
    }

    @Test
    public void testThatBookCanBeCreatedAndQueryed() {
        // NOTE: If DirtiesContext not used the ID should be incremented by 1 to prevent test failure
        Author author = buildAuthor(ID, NAME, AGE);
        authDaoImpl.create(author);
        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);
        book.setAuthorId(author.getId());

        bookDaoImpl.create(book);
        Optional<Book> result = bookDaoImpl.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatNoBookCreatedAndQueryed() {
        List<Book> result = bookDaoImpl.find();

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatManyBookCanBeCreatedAndQueryed() {
        Author author = buildAuthor(ID, NAME, AGE);
        authDaoImpl.create(author);
        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);
        bookDaoImpl.create(book);

        Book book_2 = buildBook(ISBN_2, TITLE_2, AUTHOR_ID);
        bookDaoImpl.create(book_2);

        List<Book> result = bookDaoImpl.find();

        assertThat(result).hasSize(2);

    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = buildAuthor(ID, NAME, AGE);
        authDaoImpl.create(author);

        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);
        bookDaoImpl.create(book);

        System.out.println(book);

        book.setTitle(TITLE_2);
        bookDaoImpl.update(book, ISBN);

        Optional<Book> result = bookDaoImpl.findOne(book.getIsbn());

        assertThat(result).isPresent().get().isEqualTo(book);
        System.out.println(result.get());
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = buildAuthor(ID, NAME, AGE);
        authDaoImpl.create(author);

        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);
        bookDaoImpl.create(book);

        bookDaoImpl.delete(ISBN);

        Optional<Book> result = bookDaoImpl.findOne(book.getIsbn());

        assertThat(result).isEmpty();


    }

}
