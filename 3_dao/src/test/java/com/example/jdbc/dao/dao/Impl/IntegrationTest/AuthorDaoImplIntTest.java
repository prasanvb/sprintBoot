package com.example.jdbc.dao.dao.Impl.IntegrationTest;

import com.example.jdbc.dao.dao.impl.AuthorDaoImpl;
import com.example.jdbc.dao.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.example.jdbc.dao.util.Constants.AGE;
import static com.example.jdbc.dao.util.Constants.ID;
import static com.example.jdbc.dao.util.Constants.NAME;
import static com.example.jdbc.dao.util.TestDataUtil.buildAuthor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorDaoImplIntTest {
    private final AuthorDaoImpl authorDaoImpl;

    @Autowired
    public AuthorDaoImplIntTest(AuthorDaoImpl authorDaoImpl) {
        this.authorDaoImpl = authorDaoImpl;
    }


    @Test
    public void testThatAuthorCanBeCreatedAndQueryed(){
        Author author = buildAuthor(ID, NAME, AGE);

        authorDaoImpl.create(author);
        Optional<Author> result = authorDaoImpl.findOne(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);

    }
}
