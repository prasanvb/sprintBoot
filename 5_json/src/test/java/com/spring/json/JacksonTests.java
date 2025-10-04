package com.spring.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.json.domain.Book;
import com.spring.json.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Comprehensive test suite for Jackson JSON processing within Spring Boot.
 * Tests cover serialization, deserialization, and edge cases like unknown properties.
 */
@SpringBootTest // Loads full Spring context including custom ObjectMapper bean
public class JacksonTests {

    @Autowired // Injects the custom ObjectMapper bean from JacksonConfig
    ObjectMapper objectMapper;

    /**
     * Test 1: Verifies that Jackson can serialize a Java Book object to JSON string.
     * Demonstrates @JsonProperty mapping (yearPublished -> "year") and overall structure.
     */
    @Test
    public void testObjectMapperCanCreateJsonFromJavaObject() throws JsonProcessingException {
        Book book = Utils.getBookJavaObject(); // Get sample book using builder pattern

        String result = objectMapper.writeValueAsString(book); // Jackson serializes POJO to JSON

        // Assert exact JSON match - field order and values must match exactly
        assert(result).equals("{\"isbn\":\"9780-lkw8-4785\",\"title\":\"Land of seven rivers\",\"author\":\"Sanjeev Sanyal\",\"year\":\"2012\"}");
        // Note: "year" field comes from @JsonProperty("year") annotation on yearPublished property
    }

    /**
     * Test 2: Verifies bidirectional compatibility - JSON deserializes back to equivalent Java object.
     * Ensures @JsonProperty works in reverse (JSON "year" -> yearPublished).
     */
    @Test
    public void testObjectMapperCanCreateJavaObjectFromJson() throws JsonProcessingException {
        final String json = "{\"isbn\":\"9780-lkw8-4785\",\"title\":\"Land of seven rivers\",\"author\":\"Sanjeev Sanyal\",\"year\":\"2012\"}";

        Book result = objectMapper.readValue(json, Book.class); // Jackson deserializes JSON to Book POJO

        Book book = Utils.getBookJavaObject(); // Get reference object for comparison
        assert(result).equals(book); // Uses Lombok @Data equals() implementation for object comparison
    }

    /**
     * Test 3: Tests robustness against unknown JSON properties using @JsonIgnoreProperties(ignoreUnknown = true).
     * Without this annotation, Jackson would throw UnrecognizedPropertyException.
     */
    @Test
    public void testObjectMapperCanCreateJavaObjectFromJsonWithIgnore() throws JsonProcessingException {
        // NOTE: the "foo" property is unknown to the Book class
        // but we have added the @JsonIgnoreProperties(ignoreUnknown = true) annotation
        // to the Book class, so Jackson will ignore this property and not throw an exception.
        final String json = "{\"foo\":\"bar\",\"isbn\":\"9780-lkw8-4785\",\"title\":\"Land of seven rivers\",\"author\":\"Sanjeev Sanyal\",\"year\":\"2012\"}";

        Book result = objectMapper.readValue(json, Book.class); // Jackson silently ignores unknown "foo" field

        Book book = Utils.getBookJavaObject(); // Get reference object
        assert(result).equals(book); // Assertion passes because unknown field is ignored, not missing fields
    }
}
