package ru.simbirsoft.homework.userinterface.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
public class IndexControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    @SneakyThrows
    @WithMockUser(roles = {"ADMIN"})
    public void libraryAccessSuccess() {
        this.mockMvc.perform(get("/ui/library/")
                .accept(MediaType.TEXT_HTML))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    @WithMockUser
    public void libraryAccessFail() {
        this.mockMvc.perform(get("/ui/library/")
                .accept(MediaType.TEXT_HTML))
                .andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @SneakyThrows
    public void registrationNewUser_Success() {
        this.mockMvc.perform(formLogin("/registration").user("6")
                .password("6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @SneakyThrows
    public void registrationNewUser_Fail() {
        assertTrue(this.mockMvc.perform(formLogin("/registration").user("1")
                .password("1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("Пользователь с таким именем уже существует"));
    }

    @Test
    @SneakyThrows
    public void correctLoginTest() {
        this.mockMvc.perform(formLogin().user("1")
                .password("1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void accessDeniedTest() throws Exception {
        this.mockMvc.perform(get("/ui/library"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }
}
