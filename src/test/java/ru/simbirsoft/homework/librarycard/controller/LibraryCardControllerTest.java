package ru.simbirsoft.homework.librarycard.controller;

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

import static org.hamcrest.Matchers.empty;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@Transactional
public class LibraryCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void addDays_Success() {
        this.mockMvc.perform(put("/library/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookID","1")
                .param("personID","1")
                .param("days","1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.personViewWithoutBooks.firstName",
                        Matchers.is("Олег")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.bookViewWithoutAuthor.name",
                        Matchers.is("Удивительное приключение")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expired",
                        Matchers.containsString("Возврат книги просрочен")));
    }
    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void addDays_Fail() {
        this.mockMvc.perform(put("/library/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookID","1")
                .param("personID","2")
                .param("days","1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsString("У человека с ID 2 нет книги c ID 1")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void getDebtors() {
        this.mockMvc.perform(get("/library/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].personViewWithoutBooks.firstName",
                        Matchers.is("Олег")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].bookViewWithoutAuthor.name",
                        Matchers.is("Удивительное приключение")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].expired",
                        Matchers.containsString("Возврат книги просрочен")));
    }

}
