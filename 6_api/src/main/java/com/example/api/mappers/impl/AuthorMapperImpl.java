package com.example.api.mappers.impl;

import com.example.api.domain.entity.AuthorEntity;
import com.example.api.domain.dto.AuthorDto;
import com.example.api.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapperImpl implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    public AuthorMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        if (authorEntity == null) {
            return null;
        }
        return modelMapper.map(authorEntity, AuthorDto.class);

    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        if (authorDto == null) {
            return null;
        }

        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
