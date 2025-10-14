package com.example.api.utils;

import com.example.api.domain.entity.AuthorEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UtilityMethods {

    /**
     * Converts an Iterable of AuthorEntity to a List of AuthorEntity.
     * stream method from StreamSupport class converts the iterable result to a new sequential or parallel stream from a Spliterator.
     * collect method from Stream class accumulates the elements of this stream into a List. The elements in the list will be in this stream's encounter order, if one exists.
     * Collector accumulates the input elements into a new List. There are no guarantees on the type, mutability, serializability, or thread-safety of the List.
     */
    public static List<AuthorEntity> getAuthorEntityList(Iterable<AuthorEntity> allAuthors) {
        return StreamSupport
                .stream(allAuthors.spliterator(), false)
                .collect(Collectors.toList());
    }
}
