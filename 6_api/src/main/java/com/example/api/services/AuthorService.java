package com.example.api.services;

import com.example.api.domain.entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity saveAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findById(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    Boolean isExists(Long id);

    List<AuthorEntity> findAuthorsAgeLessThan(int age);

    List<AuthorEntity> findAuthorsAgeGreaterThan(int age);

    List<AuthorEntity> findAuthorsByName(String name);


}
