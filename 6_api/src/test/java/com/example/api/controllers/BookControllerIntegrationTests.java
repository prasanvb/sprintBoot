package com.example.api.controllers;

import com.example.api.domain.entity.AuthorEntity;
import com.example.api.domain.entity.BookEntity;
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

import static com.example.api.util.Constants.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatCreateBookWithValidAuthorSavedSuccessfully() throws Exception {
        AuthorEntity testAuthorEntity = TestDataUtil.buildAuthor(TEST_ID, NAME, AGE);
        BookEntity testBookEntity = TestDataUtil.buildBook(ISBN, TITLE, testAuthorEntity);
        String bookJsonAsString = objectMapper.writeValueAsString(testBookEntity);


        String formattedUrl = String.format("/books/%s", ISBN);

        mockMvc.perform(
                MockMvcRequestBuilders.put(formattedUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJsonAsString)
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(ISBN))
        .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(TITLE));
;

    }
}
