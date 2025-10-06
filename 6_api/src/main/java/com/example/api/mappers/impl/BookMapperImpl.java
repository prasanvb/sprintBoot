package com.example.api.mappers.impl;

import com.example.api.domain.dto.BookDto;
import com.example.api.domain.entity.BookEntity;
import com.example.api.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Maps between BookEntity and BookDto using ModelMapper.
 * Spring-managed bean for entity-DTO conversion.
 */
@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {
    private final ModelMapper modelMapper;

    @Autowired
    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return bookDto != null ? modelMapper.map(bookDto, BookEntity.class) : null;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return bookEntity != null ? modelMapper.map(bookEntity, BookDto.class) : null;
    }


}
