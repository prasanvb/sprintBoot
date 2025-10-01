package com.spring.json.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("unused")
public class JacksonConfig {

    @Bean
    @SuppressWarnings("unused")
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
