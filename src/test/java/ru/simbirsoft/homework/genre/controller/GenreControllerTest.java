package ru.simbirsoft.homework.genre.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import ru.simbirsoft.homework.genre.view.GenreView;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@Transactional
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void addGenre(){
        GenreView genreView = new GenreView("Комедия");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(genreView);
        this.mockMvc.perform(post("/genre/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        Matchers.is(genreView.getName())));
    }
    @Test
    @SneakyThrows
    @WithMockUser
    public void getAll(){
        this.mockMvc.perform(get("/genre/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name",
                        Matchers.is("Ужасы")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name",
                        Matchers.is("Фантастика")));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getCount(){
        this.mockMvc.perform(get("/genre/Ужасы")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.is(6)));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void removeGenre(){
        this.mockMvc.perform(delete("/genre/")
                .param("name","Ужасы")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(containsString("Ужасы"))));
    }
}
