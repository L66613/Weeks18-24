package ru.itgirl.libraryproject18n.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itgirl.libraryproject18n.dto.*;
import ru.itgirl.libraryproject18n.model.Genre;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testGetBookByNameV1() throws Exception {
        BookDto bookDto = new BookDto();
        String bookName = "Война и мир";
        bookDto.setId(1L);
        bookDto.setName(bookName);
        bookDto.setGenre("Роман");

        mockMvc.perform(MockMvcRequestBuilders.get("/book?name={name}", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void testGetBookByNameV2() throws Exception {
        BookDto bookDto = new BookDto();
        String bookName = "Война и мир";
        bookDto.setId(1L);
        bookDto.setName(bookName);
        bookDto.setGenre("Роман");

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v2?name={name}", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void testGetBookByNameV3() throws Exception {
        BookDto bookDto = new BookDto();
        String bookName = "Война и мир";
        bookDto.setId(1L);
        bookDto.setName(bookName);
        bookDto.setGenre("Роман");

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v3?name={name}", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void testCreateBook() throws Exception {
        BookCreateDto bookCreation = new BookCreateDto();
        Genre genre = new Genre("Fantasy");

        bookCreation.setName("The Witcher");
        bookCreation.setGenre(genre);

        mockMvc.perform(MockMvcRequestBuilders.post("/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookCreation))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookCreation.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(genre.getName().toString()));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Long id = 2L;
        String name = "The Witcher";
        String genre = "Fantasy";
        BookUpdateDto bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(id);
        bookUpdateDto.setName(name);
        bookUpdateDto.setGenre(genre);

        mockMvc.perform(MockMvcRequestBuilders.put("/book/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookUpdateDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(genre));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/delete/{id}", id))
                .andExpect(status().isOk());
    }
}
