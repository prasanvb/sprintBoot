package com.spring.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.json.domain.Book;
import com.spring.json.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JacksonTests {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testObjectMapperCanCreateJsonFromJavaObject() throws JsonProcessingException {
        Book book = Utils.getBookJavaObject();

        String result = objectMapper.writeValueAsString(book);

        assert(result).equals("{\"isbn\":\"9780-lkw8-4785\",\"title\":\"Land of seven rivers\",\"author\":\"Sanjeev Sanyal\",\"year\":\"2012\"}");
    }

    @Test
    public void testObjectMapperCanCreateJavaObjectFromJson() throws JsonProcessingException {
        final String json = "{\"isbn\":\"9780-lkw8-4785\",\"title\":\"Land of seven rivers\",\"author\":\"Sanjeev Sanyal\",\"year\":\"2012\"}";

        Book result = objectMapper.readValue(json, Book.class);

        Book book = Utils.getBookJavaObject();
        assert(result).equals(book);

    }

    @Test
    public void testObjectMapperCanCreateJavaObjectFromJsonWithIgnore() throws JsonProcessingException {
        // NOTE: the "foo" property is unknown to the Book class
        // but we have added the @JsonIgnoreProperties(ignoreUnknown = true) annotation
        // to the Book class, so Jackson will ignore this property and not throw an exception.
        final String json = "{\"foo\":\"bar\",\"isbn\":\"9780-lkw8-4785\",\"title\":\"Land of seven rivers\",\"author\":\"Sanjeev Sanyal\",\"year\":\"2012\"}";

        Book result = objectMapper.readValue(json, Book.class);

        Book book = Utils.getBookJavaObject();
        assert(result).equals(book);

    }
}
