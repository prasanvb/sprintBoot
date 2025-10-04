package com.example.api.services.impl;

import com.example.api.domain.entity.AuthorEntity;
import com.example.api.repositories.AuthorRepository;
import com.example.api.services.AuthorService;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceimpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceimpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity createAuthor(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
