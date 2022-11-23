package com.example.news.controller;

import com.example.news.entity.Author;
import com.example.news.entity.Role;
import com.example.news.jwt.JwtTokenProvider;
import com.example.news.model.LoginRequest;
import com.example.news.model.RegistrationModel;
import com.example.news.repository.AuthorRepository;
import com.example.news.repository.RoleRepository;
import com.example.news.service.AuthorService;
import com.example.news.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MockMvc mockMvc;

    @Mock AuthorService authorService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AuthorRepository authorRepository;
    @Mock
    UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Test
    public void getAllUsers() throws Exception {
        String token = jwtTokenProvider.createToken("1", List.of(new Role("ROLE_ADMIN")));
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

        when(authorService.addUser(registrationModel)).thenReturn(registrationModel);

        mockMvc.perform(MockMvcRequestBuilders.post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(registrationModel)))
                .andExpect(status().isCreated());
    }

    @Test
    void testLogin() throws Exception {
        Author author = authorRepository.findById(1L).get();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setLogin("1");
        loginRequest.setPassword("1");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
        );

        when(userService.findByLogin("1")).thenReturn(author);
        mockMvc.perform(MockMvcRequestBuilders.post("/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasLength(156)));
    }
}