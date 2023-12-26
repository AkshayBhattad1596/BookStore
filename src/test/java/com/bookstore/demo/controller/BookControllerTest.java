package com.bookstore.demo.controller;

import com.bookstore.demo.entity.Book;
import com.bookstore.demo.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void getAllBooks_ReturnsListOfBooks() throws Exception {
        mockMvc.perform(get("/api/books"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void getBookById_ExistingId_ReturnsBook() throws Exception {
        Book savedBook = bookRepository.save(createTestBook());
        Long id = savedBook.getId();

        mockMvc.perform(get("/api/books/{id}", id))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id", is(id.intValue())))
               .andExpect(jsonPath("$.title", is("Test Book")));
    }

    @Test
    public void getBookById_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/books/{id}", 999))
               .andExpect(status().isNotFound());
    }

    @Test
    public void addBook_ValidBook_ReturnsAddedBook() throws Exception {
        Book bookToAdd = createTestBook();

        mockMvc.perform(post("/api/books")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(bookToAdd)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title", is("Test Book")));
    }

    @Test
    public void updateBook_ExistingIdAndValidBook_ReturnsUpdatedBook() throws Exception {
        Book savedBook = bookRepository.save(createTestBook());
        Long id = savedBook.getId();
        Book updatedBook = createTestBook();
        updatedBook.setTitle("Updated Test Book");

        mockMvc.perform(put("/api/books/{id}", id)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(updatedBook)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.title", is("Updated Test Book")));
    }

    @Test
    public void updateBook_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(put("/api/books/{id}", 999)
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(createTestBook())))
               .andExpect(status().isNotFound());
    }

    @Test
    public void deleteBook_ExistingId_ReturnsNoContent() throws Exception {
        Book savedBook = bookRepository.save(createTestBook());
        Long id = savedBook.getId();

        mockMvc.perform(delete("/api/books/{id}", id))
               .andExpect(status().isNoContent());
    }

    @Test
    public void deleteBook_NonExistingId_ReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 999))
               .andExpect(status().isNotFound());
    }

    private Book createTestBook() {
        Book book = new Book();
        book.setTitle("Test Book");
        book.setAuthor("Test Author");
        book.setISBN("1234567890");
        book.setPublishedDate(LocalDate.now());
        book.setGenre("Fiction");
        return book;
    }
}
