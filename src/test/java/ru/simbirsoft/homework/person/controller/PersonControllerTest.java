package ru.simbirsoft.homework.person.controller;

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
import ru.simbirsoft.homework.person.view.PersonViewWithoutBooks;
import ru.simbirsoft.homework.person.view.PersonViewWithoutDateAndBooks;

import java.util.Date;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@Transactional
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void addPerson(){
        PersonViewWithoutBooks personView = new PersonViewWithoutBooks(
                "Николай",
                "Илюзоров",
                "Сергеевич",
                new Date()
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(personView);
        this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        Matchers.is(personView.getFirstName())));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void editPerson(){
        PersonViewWithoutBooks personView = new PersonViewWithoutBooks(
                "Николай",
                "Илюзоров",
                "Сергеевич",
                new Date()
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(personView);
        this.mockMvc.perform(put("/person/")
                .param("id","1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        Matchers.is(personView.getFirstName())));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void removePersonById_Success() {
        this.mockMvc.perform(delete("/person/4")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.containsString("OK")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void removePersonById_Fail() {
        this.mockMvc.perform(delete("/person/1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsString("Пользователь не может быть удален, пока не вернет книги")));

    }
    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void removePersonByFIO_Success() {
        PersonViewWithoutDateAndBooks personView =  new PersonViewWithoutDateAndBooks(
                "Егор",
                "Лапин",
                "Валерьевич"
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(personView);
        this.mockMvc.perform(delete("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.containsString("OK")));

    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void removePersonByFIO_Fail() {
        PersonViewWithoutDateAndBooks personView =  new PersonViewWithoutDateAndBooks(
                "ОШИБКА",
                "ОШИБКА",
                "ОШИБКА"
        );
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(personView);
        this.mockMvc.perform(delete("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsString("Человека "
                                +personView.getFirstName()+" "
                                +personView.getLastName()+" "
                                +personView.getMiddleName()+" нет в базе данных")));
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void getById(){
        this.mockMvc.perform(get("/person/1")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name",
                        Matchers.is("Земля будущего")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].genres.[0].name",
                        Matchers.is("Ужасы")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].author.firstName",
                        Matchers.is("Иван")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name",
                        Matchers.is("Цвет красок")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void borrowBook_Success(){
        this.mockMvc.perform(put("/person/borrow")
                .param("personId","4")
                .param("bookName","За горизонтом")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        Matchers.is("Егор")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.[0].name",
                        Matchers.is("За горизонтом")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void borrowBook_Fail(){
        this.mockMvc.perform(put("/person/borrow")
                .param("personId","1")
                .param("bookName","За горизонтом")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsString("У Человека с ID 1 есть просроченная книга")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void returnBook_Success(){
        this.mockMvc.perform(put("/person/return")
                .param("personId","1")
                .param("bookName","Земля будущего")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        Matchers.is("Олег")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.books.[*].name",
                        Matchers.not(hasItem("Земля будущего"))));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void returnBook_Fail(){
        this.mockMvc.perform(put("/person/return")
                .param("personId","2")
                .param("bookName","Земля будущего")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error",
                        Matchers.containsString("Книга 'Земля будущего' не у человека c ID 2")));
    }

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void getAll(){
        this.mockMvc.perform(get("/person/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*]",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName",
                        Matchers.is("Олег")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].books.[*]",
                        Matchers.not(empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].firstName",
                        Matchers.is("Иван")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].books.[*]",
                        Matchers.not(empty())));
    }
}
