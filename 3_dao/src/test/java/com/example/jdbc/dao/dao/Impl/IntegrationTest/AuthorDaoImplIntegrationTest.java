package com.example.jdbc.dao.dao.Impl.IntegrationTest;

import com.example.jdbc.dao.dao.impl.AuthorDaoImpl;
import com.example.jdbc.dao.domain.Author;
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
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntegrationTest {
    private final AuthorDaoImpl authorDaoImpl;

    @Autowired
    public AuthorDaoImplIntegrationTest(AuthorDaoImpl authorDaoImpl) {
        this.authorDaoImpl = authorDaoImpl;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndQueryed(){
        Author author = buildAuthor(ID, NAME, AGE);

        authorDaoImpl.create(author);
        Optional<Author> result = authorDaoImpl.findOne(author.getId());

        System.out.println(result);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatNoAuthorCreatedAndQueryed() {
        List<Author> result = authorDaoImpl.find();



        assertThat(result).isEmpty();
    }

    @Test
    public void testThatManyAuthorCanBeCreatedAndQueryed() {
        Author author = buildAuthor(ID, NAME, AGE);
        authorDaoImpl.create(author);

        Author author_2 = buildAuthor(ID_2, NAME_2, AGE_2);
        authorDaoImpl.create(author_2);

        Author author_3 = buildAuthor(ID_3, NAME_3, AGE_3);
        authorDaoImpl.create(author_3);

        List<Author> result = authorDaoImpl.find();

        System.out.println(result);

        assertThat(result).hasSize(3).containsExactly(author, author_2, author_3);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        Author author = buildAuthor(ID, NAME, AGE);
        authorDaoImpl.create(author);

        author.setName("APJ");
        authorDaoImpl.update(author, ID);

        Optional<Author> result = authorDaoImpl.findOne(author.getId());

        System.out.println(result);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);

    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = buildAuthor(ID, NAME, AGE);
        authorDaoImpl.create(author);

        authorDaoImpl.delete(ID);

        Optional<Author> result = authorDaoImpl.findOne(author.getId());

        assertThat(result).isEmpty();
    }

}