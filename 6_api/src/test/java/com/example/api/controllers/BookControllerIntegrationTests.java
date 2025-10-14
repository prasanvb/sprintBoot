package com.example.api.controllers;

import com.example.api.domain.entity.AuthorEntity;
import com.example.api.domain.entity.BookEntity;
import com.example.api.services.BookService;
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

import static com.example.api.path.BookPaths.BOOKS;
import static com.example.api.path.BookPaths.bookByIsbnUrl;
import static com.example.api.util.Constants.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookService bookService;
    private final AuthorEntity testAuthorEntity = TestDataUtil.buildAuthor(null, NAME, AGE);
    private final BookEntity testBookEntity = TestDataUtil.buildBook(ISBN, TITLE, testAuthorEntity);


    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookWithValidAuthorSavedSuccessfully() throws Exception {

        String bookJsonAsString = objectMapper.writeValueAsString(TestDataUtil.buildBook(ISBN_2, TITLE, testAuthorEntity));

        mockMvc.perform(
                        MockMvcRequestBuilders.put(bookByIsbnUrl(ISBN_2))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJsonAsString)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(ISBN_2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(TITLE));
    }

    @Test
    public void testThatUpdateBookWithValidAuthorSavedSuccessfully() throws Exception {
        BookEntity createBookEntity = bookService.saveBook(testBookEntity);

        BookEntity updateBookEntity = TestDataUtil.buildBook(createBookEntity.getIsbn(), TITLE+"updated", testAuthorEntity);

        String bookJsonAsString = objectMapper.writeValueAsString(updateBookEntity);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(bookByIsbnUrl(ISBN))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJsonAsString)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(updateBookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updateBookEntity.getTitle()));
    }

    @Test
    public void testThatListBooksReturnsHttpsStatus200() throws Exception {
        bookService.saveBook(testBookEntity);

        mockMvc.perform(
                        MockMvcRequestBuilders.get(BOOKS)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isbn").value(ISBN))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(TITLE));
    }

    @Test
    public void testThatFindBooksByIsbnReturnsHttpsStatus200() throws Exception {
        BookEntity createBookEntity = bookService.saveBook(testBookEntity);

        mockMvc.perform(
                        MockMvcRequestBuilders.get(bookByIsbnUrl(createBookEntity.getIsbn()))
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(ISBN))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(TITLE));
    }

    @Test
    public void testThatPartialUpdateBookWithValidAuthorSavedSuccessfully() throws Exception {
        BookEntity createBookEntity = bookService.saveBook(testBookEntity);

        BookEntity updateBookEntity = TestDataUtil.buildBook(createBookEntity.getIsbn(), TITLE+" partial", testAuthorEntity);

        String bookJsonAsString = objectMapper.writeValueAsString(updateBookEntity);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch(bookByIsbnUrl(ISBN))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bookJsonAsString)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value(updateBookEntity.getIsbn()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(updateBookEntity.getTitle()));
    }

    @Test
    public void testThatDeleteBookByIsbnReturnsHttpsStatus204() throws Exception {
        bookService.saveBook(TestDataUtil.buildBook(ISBN_2, TITLE, testAuthorEntity));

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(bookByIsbnUrl(ISBN_2))
                ).andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(bookByIsbnUrl(ISBN_2))
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
