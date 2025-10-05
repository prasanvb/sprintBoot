package com.example.api.mappers.impl;

import com.example.api.domain.entity.AuthorEntity;
import com.example.api.domain.dto.AuthorDto;
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
     * @param modelMapper the ModelMapper instance injected by Spring for object mapping
     */
    @Autowired
    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Maps an AuthorDto to an AuthorEntity.
     * @param authorDto the AuthorDto to map
     * @return the corresponding AuthorEntity, or null if the input is null
     */
    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }

        authorDto.setDetails("My name is " + authorDto.getName() + " and I am " + authorDto.getAge() + " years old.");
        return modelMapper.map(authorDto, AuthorEntity.class);
    }

    /**
     * Maps an AuthorEntity to an AuthorDto.
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
