package ru.simbirsoft.homework.author.controller;

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
import ru.simbirsoft.homework.author.view.AuthorView;
import ru.simbirsoft.homework.book.view.BookViewWithoutAuthor;
import ru.simbirsoft.homework.genre.view.GenreView;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@Transactional
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void addAuthor(){
        GenreView genreView= new GenreView("Комедия");
        Set<GenreView> genreSet = Collections.singleton(genreView);
        BookViewWithoutAuthor bookViewWithoutAuthor = new BookViewWithoutAuthor(
                "Долина",
                new Date(),
                genreSet
        );
        AuthorView authorView = new AuthorView(
                "Андрей",
                "Лоскутов",
                "Егорович",
                new Date(),
                Collections.singletonList(bookViewWithoutAuthor)
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(authorView);
        this.mockMvc.perform(post("/authors/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        Matchers.is(authorView.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        Matchers.is(authorView.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.[0].name",
                        Matchers.is(bookViewWithoutAuthor.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.[0].genres.[0].name",
                        Matchers.is(genreView.getName())));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void removeAuthor_Success(){
        this.mockMvc.perform(delete("/authors/4")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.is("OK")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void removeAuthor_Fail(){
        this.mockMvc.perform(delete("/authors/1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsString("данного автора находится у человека")));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getAllAuthors(){
        this.mockMvc.perform(get("/authors/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName",
                        Matchers.is("Илья")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName",
                        Matchers.is("Дмитрий")));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getAuthorsBook(){
        this.mockMvc.perform(get("/authors/1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        Matchers.is("Илья")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.[*]",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.[0].name",
                        Matchers.is("Цвет красок")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.[0].genres[*]",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.[0].genres[0].name",
                        Matchers.is("Ужасы")));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getAuthorsByBirthDateAndFIO(){
        this.mockMvc.perform(get("/authors/1")
                .param("firstName", "Илья")
                .param("to", "2019-11-19")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        Matchers.is("Илья")));
    }
}
