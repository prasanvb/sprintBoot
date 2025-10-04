package com.example.db.repositories;

import com.example.db.domain.Author;
import com.example.api.ApiApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static com.example.db.util.Constants.*;
import static com.example.db.util.TestDataUtil.buildAuthor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ApiApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorRepositoryIntegrationTest {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryIntegrationTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndQueryed() {
        Author author = buildAuthor(null, NAME, AGE);

        Author savedAuthor = authorRepository.save(author);
        Optional<Author> result = authorRepository.findById(savedAuthor.getId());

        System.out.println(result);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedAuthor);
    }

    @Test
    public void testThatNoAuthorCreatedAndQueryed() {
        Iterable<Author> result = authorRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatManyAuthorCanBeCreatedAndQueryed() {
        Author author = buildAuthor(null, NAME, AGE);
        Author author_2 = buildAuthor(null, NAME_2, AGE_2);
        Author author_3 = buildAuthor(null, NAME_3, AGE_3);

        Iterable<Author> authorList = List.of(author, author_2, author_3);
        authorRepository.saveAll(authorList);

        System.out.println(authorList);

        Iterable<Author> result = authorRepository.findAll();

        System.out.println(result);

        assertThat(result).hasSize(3).containsExactly(author, author_2, author_3);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = buildAuthor(null, NAME, AGE);
        authorRepository.save(author);
        System.out.println(author);

        author.setName("APJ");
        authorRepository.save(author);

        Optional<Author> result = authorRepository.findById(author.getId());

        System.out.println(result);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);

    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        Author author = buildAuthor(null, NAME, AGE);
        authorRepository.save(author);

        authorRepository.deleteById(author.getId());

        Optional<Author> result = authorRepository.findById(author.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorWithAgeLessThan() {
        Author author = buildAuthor(null, NAME, AGE);
        Author author_2 = buildAuthor(null, NAME_2, AGE_2);
        Author author_3 = buildAuthor(null, NAME_3, AGE_3);

        Iterable<Author> authorList = List.of(author, author_2, author_3);
        authorRepository.saveAll(authorList);

        Iterable<Author> result = authorRepository.ageLessThan(50);

        assertThat(result).hasSize(1).containsExactly(author_3);
    }

    @Test
    public void testThatGetAuthorWithAgeGreaterThan() {
        Author author = buildAuthor(null, NAME, AGE);
        Author author_2 = buildAuthor(null, NAME_2, AGE_2);
        Author author_3 = buildAuthor(null, NAME_3, AGE_3);

        Iterable<Author> authorList = List.of(author, author_2, author_3);
        authorRepository.saveAll(authorList);

        Iterable<Author> result = authorRepository.findAuthorsAgeGreaterThan(50);

        assertThat(result).hasSize(2).containsExactly(author, author_2);
    }

    @Test
    public void testThatGetAuthorWithNameExists() {
        Author author = buildAuthor(null, NAME, AGE);
        Author author_2 = buildAuthor(null, NAME_2, AGE_2);
        Author author_3 = buildAuthor(null, NAME_3, AGE_3);

        Iterable<Author> authorList = List.of(author, author_2, author_3);
        authorRepository.saveAll(authorList);

        Iterable<Author> result = authorRepository.findAuthorByName(NAME);

        System.out.println(result);
        assertThat(result).hasSize(1).containsExactly(author);
    }

}
