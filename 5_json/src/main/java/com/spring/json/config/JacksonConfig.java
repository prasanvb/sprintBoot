package com.spring.json.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jackson configuration to customize JSON processing behavior in Spring Boot application.
 * While Spring Boot auto-configures Jackson, custom beans allow centralized control over serialization/deserialization.
 */
@Configuration // Spring: Marks this class as a source of bean definitions
@SuppressWarnings("unused") // Suppresses IDE warnings for unused parameters (optional)
public class JacksonConfig {

    /**
     * Defines a custom ObjectMapper bean that can be injected anywhere in the application.
     * Enables programmatic JSON handling with consistent configuration.
     *
     * @return ObjectMapper instance for JSON processing
     */
    @Bean // Spring: Registers this method's return value as a Spring bean
    @SuppressWarnings("unused") // Suppresses unused method warning when not directly called
    public ObjectMapper objectMapper() {
        // Basic ObjectMapper creation - can be extended with custom configurations like:
        // objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        // objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new ObjectMapper();
    }
}
