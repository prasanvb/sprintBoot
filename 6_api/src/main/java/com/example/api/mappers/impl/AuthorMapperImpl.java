package com.example.api.mappers.impl;

import com.example.api.domain.dto.AuthorDto;
import com.example.api.domain.entity.AuthorEntity;
import com.example.api.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of the Mapper interface for converting between AuthorEntity and AuthorDto.
 * Uses ModelMapper for automatic field mapping between the entity and DTO objects.
 * This component is registered as a Spring bean for dependency injection.
 */
@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    /**
     * Constructor for AuthorMapperImpl.
     *
     * @param modelMapper the ModelMapper instance injected by Spring for object mapping
     */
    @Autowired
    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Maps an AuthorDto to an AuthorEntity.
     *
     * @param authorDto the AuthorDto to map
     * @return the corresponding AuthorEntity, or null if the input is null
     */
    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }

        authorDto.setDetails("My name is " + authorDto.getName() + " and I am " + authorDto.getAge() + " years old.");

        //  ModelMapper API requires the target class type as a `Class<?>` object, not an instance of the class.

        //  `.class` is Java's way of getting the `Class` object that represents the `AuthorEntity` type at runtime.
        //  This `Class` object contains all the metadata about the `AuthorEntity` class that ModelMapper needs to:
        //
        //  1. Determine the target type's structure
        //  2. Access its properties through reflection
        //  3. Create a new instance of `AuthorEntity`
        //  4. Map matching fields from the source (`authorDto`) to the target type
        //
        //  Simply using `AuthorEntity` alone wouldn't work as it's just a type name,
        //  not a `Class` object that ModelMapper can use for reflection and instantiation.
        return modelMapper.map(authorDto, AuthorEntity.class);
    }

    /**
     * Maps an AuthorEntity to an AuthorDto.
     *
     * @param authorEntity the AuthorEntity to map
     * @return the corresponding AuthorDto, or null if the input is null
     */
    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        if (authorEntity == null) {
            return null;
        }
        return modelMapper.map(authorEntity, AuthorDto.class);

    }


}
