package com.example.api.controllers;

import com.example.api.domain.dto.AuthorDto;
import com.example.api.domain.entity.AuthorEntity;
import com.example.api.mappers.Mapper;
import com.example.api.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.createAuthor(authorEntity);
        AuthorDto savedAuthorDto = authorMapper.mapTo(savedAuthorEntity);
        return new ResponseEntity<>(savedAuthorDto,HttpStatus.CREATED);
    }

    @GetMapping(path="/list-authors")
    public List<AuthorDto> listAuthors(){
        List<AuthorEntity> authorEntities = authorService.findAll();
        List<AuthorDto> authorDtos = new ArrayList<>();
        for(AuthorEntity authorEntity : authorEntities){
            authorDtos.add(authorMapper.mapTo(authorEntity));
        }

        return authorDtos;
    }

    @GetMapping(path="/authors")
    public List<AuthorDto> authors(){
        List<AuthorEntity> authorEntities = authorService.findAll();
        return authorEntities  // Start with the list of AuthorEntity objects
                .stream()  // Convert list to Stream for functional operations
                .map(authorMapper::mapTo)  // Transform each AuthorEntity to AuthorDto using the mapper
                .collect(Collectors.toList());  // Collect the mapped DTOs into a List<AuthorDto>

    }

    @GetMapping(path="/authors/{id}")
    public ResponseEntity<AuthorDto> authorById(@PathVariable("id") Long id){
        Optional<AuthorEntity> foundAuthorEntity = authorService.findById(id);

        return foundAuthorEntity.map(authorEntity -> {
            AuthorDto foundAuthorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(foundAuthorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
