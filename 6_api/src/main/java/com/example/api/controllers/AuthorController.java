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

import static com.example.api.path.AuthorPaths.*;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = AUTHORS)
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.saveAuthor(authorEntity);
        AuthorDto savedAuthorDto = authorMapper.mapTo(savedAuthorEntity);
        return new ResponseEntity<>(savedAuthorDto, HttpStatus.CREATED);
    }

    @GetMapping(path = LIST_AUTHORS)
    public List<AuthorDto> listAuthors() {
        List<AuthorEntity> authorEntities = authorService.findAll();
        List<AuthorDto> authorDtolist = new ArrayList<>();
        for (AuthorEntity authorEntity : authorEntities) {
            authorDtolist.add(authorMapper.mapTo(authorEntity));
        }

        return authorDtolist;
    }

    private List<AuthorDto> getAuthorDtoList(List<AuthorEntity> authorEntities) {
        return authorEntities  // Start with the list of AuthorEntity objects
                .stream()  // Convert list to Stream for functional operations
                .map(authorMapper::mapTo)  // Transform each AuthorEntity to AuthorDto using the mapper
                .collect(Collectors.toList()); // Collect the mapped DTOs into a List<AuthorDto>
    }

    @GetMapping(path = AUTHORS)
    public List<AuthorDto> authors() {
        List<AuthorEntity> authorEntities = authorService.findAll();
        return getAuthorDtoList(authorEntities);

    }

    @GetMapping(path = AUTHOR_BY_ID)
    public ResponseEntity<AuthorDto> authorById(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<AuthorEntity> foundAuthorEntity = authorService.findById(id);

        return foundAuthorEntity.map(authorEntity -> {
            AuthorDto foundAuthorDto = authorMapper.mapTo(authorEntity);
            return new ResponseEntity<>(foundAuthorDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = AUTHORS_AGE_LESS_THAN)
    public List<AuthorDto> findAuthorsAgeLessThan(@PathVariable int age) {
        List<AuthorEntity> authorEntities = authorService.findAuthorsAgeLessThan(age);
        return getAuthorDtoList(authorEntities);
    }

    @GetMapping(path = AUTHORS_AGE_GREATER_THAN)
    public List<AuthorDto> findAuthorsAgeGreaterThan(@PathVariable int age) {
        List<AuthorEntity> authorEntities = authorService.findAuthorsAgeGreaterThan(age);
        return getAuthorDtoList(authorEntities);
    }

    @GetMapping(path = AUTHORS_BY_NAME)
    public ResponseEntity<List<AuthorDto>> findAuthorsByName(@RequestParam("name") String name) {
        if (name == null || name.trim().isEmpty() || name.trim().equals(",")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<AuthorEntity> authorEntities = authorService.findAuthorsByName(name);
        return new ResponseEntity<>(getAuthorDtoList(authorEntities), HttpStatus.OK);
    }

    @PutMapping(path = UPDATE_AUTHOR_BY_ID)
    public ResponseEntity<AuthorDto> updateAuthorById(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorDto.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthorEntity = authorService.saveAuthor(authorEntity);
        AuthorDto savedAuthorDto = authorMapper.mapTo(savedAuthorEntity);

        return new ResponseEntity<>(savedAuthorDto, HttpStatus.OK);
    }

     @PatchMapping(path = PATCH_AUTHOR_BY_ID)
     public ResponseEntity<AuthorDto> patchAuthorById(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
         if (id == null) {
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }

         if (!authorService.isExists(id)) {
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }

         authorDto.setId(id);
         AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
         AuthorEntity savedAuthorEntity = authorService.partialUpdate(id, authorEntity);
         AuthorDto savedAuthorDto = authorMapper.mapTo(savedAuthorEntity);

         return new ResponseEntity<>(savedAuthorDto, HttpStatus.OK);
     }

     @DeleteMapping(path = AUTHOR_BY_ID)
     public ResponseEntity<Void> deleteAuthorById(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (!authorService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        authorService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
