package com.example.jdbc.dao.dao.Impl.IntegrationTest;

import com.example.jdbc.dao.dao.impl.AuthorDaoImpl;
import com.example.jdbc.dao.dao.impl.BookDaoImpl;
import com.example.jdbc.dao.domain.Author;
import com.example.jdbc.dao.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.example.jdbc.dao.util.Constants.*;
import static com.example.jdbc.dao.util.TestDataUtil.buildAuthor;
import static com.example.jdbc.dao.util.TestDataUtil.buildBook;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BookDaoImplIntegrationTest {
    private final AuthorDaoImpl authDaoImpl;
    private final BookDaoImpl bookDaoImpl;

    @Autowired
    public BookDaoImplIntegrationTest(AuthorDaoImpl authDaoImp, BookDaoImpl bookDaoImp) {
        this.authDaoImpl = authDaoImp;
        this.bookDaoImpl = bookDaoImp;
    }

    @Test
    public void testThatBookCanBeCreatedAndQueryed(){
        // NOTE: ID is incremented by 1 to prevent test failure
        Author author = buildAuthor(ID+1, NAME, AGE);
        authDaoImpl.create(author);
        Book book = buildBook(ISBN, TITLE, AUTHOR_ID);
        book.setAuthorId(author.getId());

        bookDaoImpl.create(book);
        Optional<Book> result = bookDaoImpl.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }
}
