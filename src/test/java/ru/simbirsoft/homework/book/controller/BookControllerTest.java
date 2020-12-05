package ru.simbirsoft.homework.book.controller;

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
import ru.simbirsoft.homework.author.view.AuthorWithoutBooks;
import ru.simbirsoft.homework.book.view.BookView;
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
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void addBook() {
        GenreView genreView = new GenreView("Комедия");
        Set<GenreView> genreSet = Collections.singleton(genreView);
        AuthorWithoutBooks authorView = new AuthorWithoutBooks(
                "Андрей",
                "Лоскутов",
                "Егорович",
                new Date()
        );
        BookView bookView = new BookView(
                "Дорога",
                new Date(),
                genreSet,
                authorView
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bookView);
        this.mockMvc.perform(post("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        Matchers.is(bookView.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genres.[0].name",
                        Matchers.is(genreView.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.firstName",
                        Matchers.is(authorView.getFirstName())));
    }


    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void removeBook_Success() {
        this.mockMvc.perform(delete("/books/")
                .param("id","4")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.is("OK")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void removeBook_Fail() {
        this.mockMvc.perform(delete("/books/")
                .param("id","1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsString("Книгу нельзя удалить, пока она у человека")));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getBooksByAuthor() {
        String firstName = "Илья";
        String middleName = "Витальевич";
        this.mockMvc.perform(get("/books/")
                .param("firstName", firstName)
                .param("middleName", middleName)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name",
                        Matchers.is("Цвет красок")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].genres.[0].name",
                        Matchers.is("Ужасы")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author.firstName",
                        Matchers.is(firstName)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author.middleName",
                        Matchers.is(middleName)));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getBooksByGenre() {
        this.mockMvc.perform(get("/books/Ужасы")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name",
                        Matchers.is("Удивительное приключение")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].genres.[*].name",
                        Matchers.hasItem("Ужасы")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author.firstName",
                        Matchers.is("Илья")));
    }


    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void editGenre_Success() {
        GenreView genreView = new GenreView("Комедия");
        Set<GenreView> genreSet = Collections.singleton(genreView);
        BookViewWithoutAuthor bookViewWithoutAuthor = new BookViewWithoutAuthor(
                "Цвет красок",
                new Date(),
                genreSet
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bookViewWithoutAuthor);
        this.mockMvc.perform(put("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name",
                        Matchers.is(bookViewWithoutAuthor.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genres.[0].name",
                        Matchers.is(genreView.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author.firstName",
                        Matchers.is("Илья")));

    }
    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void editGenre_Fail() {
        GenreView genreView = new GenreView("Комедия");
        Set<GenreView> genreSet = Collections.singleton(genreView);
        BookViewWithoutAuthor bookViewWithoutAuthor = new BookViewWithoutAuthor(
                "ОШИБКА",
                new Date(),
                genreSet
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bookViewWithoutAuthor);
        this.mockMvc.perform(put("/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsString("Книги c названием "
                                +bookViewWithoutAuthor.getName()+" нет в базе")));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getBooksByGenreAndDate() {
        this.mockMvc.perform(get("/books/filter")
                .param("genre", "Ужасы")
                .param("year", "2015")
                .param("attribute", "BEFORE")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name",
                        Matchers.is("Земля будущего")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].genres.[0].name",
                        Matchers.is("Ужасы")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author.firstName",
                        Matchers.is("Иван")));
    }
}
