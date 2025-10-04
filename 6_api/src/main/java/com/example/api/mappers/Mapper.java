package com.example.api.mappers;

import com.example.api.domain.dto.AuthorDto;

public interface Mapper<A, B> {
    B mapTo(A a);
    A mapFrom(B b);

}
