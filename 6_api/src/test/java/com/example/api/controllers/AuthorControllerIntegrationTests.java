package com.example.api.controllers;

import com.example.api.domain.entity.AuthorEntity;
import com.example.api.util.TestDataUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.example.api.util.Constants.AGE;
import static com.example.api.util.Constants.NAME;

// Loads the full Spring application context for integration testing
@SpringBootTest
// Integrates JUnit 5 with the Spring TestContext Framework
@ExtendWith(SpringExtension.class)
// Resets the application context after each test method to ensure test isolation
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
// Autoconfigures MockMvc for testing Spring MVC controllers without starting a server
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {
    // The field can be declared as `final` because it is only assigned once in the constructor and never reassigned elsewhere in the class.
    // Making it `final` enforces immutability, ensuring that its reference cannot be changed after construction, which is a good practice for dependency-injected fields.
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    // NOTE: You do not need to manually initialize `ObjectMapper` class constructor if you annotate the field with `@Autowired`.
    // Spring Boot will inject the `ObjectMapper` bean defined in `MapperConfig` automatically.
    // Your current constructor-based injection is correct and will use the bean from `MapperConfig`
    // as long as it is annotated with `@Configuration` and provides an `@Bean` of type `ObjectMapper`.
    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

@Test
public void testThatCreateAuthorReturnsHttp201AndSavedAuthorSuccessfully() throws Exception {
    // Build a test AuthorEntity and serialize it to JSON
    AuthorEntity testAuthorEntity = TestDataUtil.buildAuthor(null, NAME, AGE);
    String authorJsonAsString = objectMapper.writeValueAsString(testAuthorEntity);

    // Perform POST /authors and verify the response
    mockMvc.perform(
            MockMvcRequestBuilders.post("/authors")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(authorJsonAsString)
    )
    // Expect HTTP 201 Created status
    .andExpect(MockMvcResultMatchers.status().isCreated())
    .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
    .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(NAME))
    .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(AGE));
}
}
