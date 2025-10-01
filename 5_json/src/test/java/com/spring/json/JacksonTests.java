package com.spring.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.json.domain.Book;
import com.spring.json.utils.Utils;
import org.junit.jupiter.api.Test;

public class JacksonTests {

    @Test
    public void testObjectMapperCanCreateJsonFromJavaObject() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();

        Book book = Utils.getBookJavaObject();

        String result = objectMapper.writeValueAsString(book);

        assert(result).equals("{\"isbn\":\"9780-lkw8-4785\",\"title\":\"Land of seven rivers\",\"author\":\"Sanjeev Sanyal\",\"yearPublished\":\"2012\"}");
    }

    @Test
    public void testObjectMapperCanCreateJavaObjectFromJson() throws JsonProcessingException {
        final String json = "{\"isbn\":\"9780-lkw8-4785\",\"title\":\"Land of seven rivers\",\"author\":\"Sanjeev Sanyal\",\"yearPublished\":\"2012\"}";
        final ObjectMapper objectMapper = new ObjectMapper();

        Book result = objectMapper.readValue(json, Book.class);

        Book book = Utils.getBookJavaObject();
        assert(result).equals(book);

    }
}
