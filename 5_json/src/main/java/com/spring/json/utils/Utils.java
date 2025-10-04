package com.spring.json.utils;

import com.spring.json.domain.Book;

/**
 * Utility class providing helper methods for creating sample data objects.
 * Used by controllers and tests to standardize sample book instances.
 */
public class Utils {

    /**
     * Creates a sample Book object using Lombok's builder pattern.
     * Demonstrates fluent API for object construction.
     * Used in controller responses and test assertions.
     *
     * @return Book instance with predefined sample data
     */
    public static Book getBookJavaObject() {
        return Book.builder() // Lombok builder pattern for fluent creation
                .isbn("9780-lkw8-4785") // International Standard Book Number
                .title("Land of seven rivers") // Book title
                .author("Sanjeev Sanyal") // Author name
                .yearPublished("2012") // Publication year (mapped to JSON "year")
                .build(); // Finalizes and returns the constructed Book object
    }
}
