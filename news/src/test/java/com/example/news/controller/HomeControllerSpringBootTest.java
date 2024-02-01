package com.example.news.controller;

import com.example.news.entity.Author;
import com.example.news.jwt.JwtTokenProvider;
import com.example.news.model.RegistrationModel;
import com.example.news.repository.AuthorRepository;
import com.example.news.service.AuthorService;
import com.example.news.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerSpringBootTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    UserService userService;
    @MockBean
    AuthorService authorService;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        Author author = authorRepository.findAll().stream()
                .filter(a -> a.getRoles().stream()
                        .filter(r -> r.getName().equals("ROLE_ADMIN"))
                        .toList().size() > 0)
                .findFirst()
                .get();

        String token = jwtTokenProvider.createToken(author.getLogin(), author.getRoles());
        Authentication authentication = jwtTokenProvider.getAuth(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .header("Authorization", "Bearer_" + token));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void registration() throws Exception {
        RegistrationModel registrationModel =
                RegistrationModel.builder()
                        .email("ivanov@gmail.com")
                        .login("Ivanov")
                        .name("Ivan")
                        .password("ivanov")
                        .surname("ivanov")
                        .role("ROLE_USER")
                        .build();

        when(authorService.addUser(registrationModel)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(registrationModel)))
                .andExpect(status().isCreated());
    }
}