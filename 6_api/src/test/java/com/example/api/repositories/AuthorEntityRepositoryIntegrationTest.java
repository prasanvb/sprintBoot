package com.example.api.repositories;

import com.example.api.ApiApplication;
import com.example.api.domain.entity.AuthorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static com.example.api.util.Constants.*;
import static com.example.api.util.TestDataUtil.buildAuthor;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ApiApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorEntityRepositoryIntegrationTest {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorEntityRepositoryIntegrationTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndQueryed() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);

        AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
        Optional<AuthorEntity> result = authorRepository.findById(savedAuthorEntity.getId());

        System.out.println(result);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedAuthorEntity);
    }

    @Test
    public void testThatNoAuthorCreatedAndQueryed() {
        Iterable<AuthorEntity> result = authorRepository.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatManyAuthorCanBeCreatedAndQueryed() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        AuthorEntity author_Entity_2 = buildAuthor(null, NAME_2, AGE_2);
        AuthorEntity author_Entity_3 = buildAuthor(null, NAME_3, AGE_3);

        Iterable<AuthorEntity> authorList = List.of(authorEntity, author_Entity_2, author_Entity_3);
        authorRepository.saveAll(authorList);

        System.out.println(authorList);

        Iterable<AuthorEntity> result = authorRepository.findAll();

        System.out.println(result);

        assertThat(result).hasSize(3).containsExactly(authorEntity, author_Entity_2, author_Entity_3);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        authorRepository.save(authorEntity);
        System.out.println(authorEntity);

        authorEntity.setName("APJ");
        authorRepository.save(authorEntity);

        Optional<AuthorEntity> result = authorRepository.findById(authorEntity.getId());

        System.out.println(result);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(authorEntity);

    }

    @Test
    public void testThatAuthorCanBeDeleted() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        authorRepository.save(authorEntity);

        authorRepository.deleteById(authorEntity.getId());

        Optional<AuthorEntity> result = authorRepository.findById(authorEntity.getId());

        assertThat(result).isEmpty();
    }

    @Test
    public void testThatGetAuthorWithAgeLessThan() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        AuthorEntity author_Entity_2 = buildAuthor(null, NAME_2, AGE_2);
        AuthorEntity author_Entity_3 = buildAuthor(null, NAME_3, AGE_3);

        Iterable<AuthorEntity> authorList = List.of(authorEntity, author_Entity_2, author_Entity_3);
        authorRepository.saveAll(authorList);

        Iterable<AuthorEntity> result = authorRepository.ageLessThan(50);

        assertThat(result).hasSize(1).containsExactly(author_Entity_3);
    }

    @Test
    public void testThatGetAuthorWithAgeGreaterThan() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        AuthorEntity author_Entity_2 = buildAuthor(null, NAME_2, AGE_2);
        AuthorEntity author_Entity_3 = buildAuthor(null, NAME_3, AGE_3);

        Iterable<AuthorEntity> authorList = List.of(authorEntity, author_Entity_2, author_Entity_3);
        authorRepository.saveAll(authorList);

        Iterable<AuthorEntity> result = authorRepository.findAuthorsAgeGreaterThan(50);

        assertThat(result).hasSize(2).containsExactly(authorEntity, author_Entity_2);
    }

    @Test
    public void testThatGetAuthorWithNameExists() {
        AuthorEntity authorEntity = buildAuthor(null, NAME, AGE);
        AuthorEntity author_Entity_2 = buildAuthor(null, NAME_2, AGE_2);
        AuthorEntity author_Entity_3 = buildAuthor(null, NAME_3, AGE_3);

        Iterable<AuthorEntity> authorList = List.of(authorEntity, author_Entity_2, author_Entity_3);
        authorRepository.saveAll(authorList);

        Iterable<AuthorEntity> result = authorRepository.findAuthorByName(NAME);

        System.out.println(result);
        assertThat(result).hasSize(1).containsExactly(authorEntity);
    }

}
