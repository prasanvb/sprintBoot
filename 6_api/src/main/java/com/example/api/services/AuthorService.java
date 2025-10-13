package com.example.api.services;

import com.example.api.domain.entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findById(Long id);

}
