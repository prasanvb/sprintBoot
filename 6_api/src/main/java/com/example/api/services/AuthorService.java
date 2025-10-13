package com.example.api.services;

import com.example.api.domain.entity.AuthorEntity;

import java.util.List;

public interface   AuthorService {

    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

}
