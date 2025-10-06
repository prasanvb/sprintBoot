package com.example.api.mappers.impl;

import com.example.api.domain.dto.BookDto;
import com.example.api.domain.entity.BookEntity;
import com.example.api.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements Mapper<BookEntity, BookDto> {
    private final ModelMapper modelMapper;


    @Autowired
    public BookMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        if (bookDto == null) {
            return null;
        }
        return modelMapper.map(bookDto, BookEntity.class);

    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }
        return modelMapper.map(bookEntity, BookDto.class);
    }


}
