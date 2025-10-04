package com.spring.json.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Book domain model representing a book entity in the library system.
 * Demonstrates Jackson annotations integration with Lombok boilerplate reduction.
 */
@Data // Lombok: Generates getters, setters, equals(), hashCode(), toString()
@AllArgsConstructor // Lombok: Creates constructor with all fields (used for builder)
@NoArgsConstructor // Lombok: Required for Jackson deserialization (no-arg constructor needed)
@Builder // Lombok: Enables builder pattern for fluent object creation
@JsonIgnoreProperties(ignoreUnknown = true) // Jackson: Ignores unknown JSON properties (e.g., "foo") instead of throwing exception
public class Book {

    private String isbn;

    private String title;

    private String author;

    @JsonProperty("year") // Jackson: Maps JSON field "year" to this Java property, allows different naming
    private String yearPublished;
}
