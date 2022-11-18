package com.example.news.controller;

import com.example.news.entity.Author;
import com.example.news.model.PostModel;
import com.example.news.model.RegistrationModel;
import com.example.news.service.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class HomeControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @BeforeEach
    public void init() {
        openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
    }

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private HomeController homeController;

    @Test
    public void createAuthor() throws Exception {
        RegistrationModel registrationModel = new RegistrationModel();
        registrationModel.setEmail("2");
        registrationModel.setName("2");
        registrationModel.setRole("ROLE_ADMIN");
        registrationModel.setLogin("22");
        registrationModel.setPassword("2");
        registrationModel.setSurname("2");

        when(authorService.addUser(registrationModel)).thenReturn(registrationModel);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(registrationModel));

        mockMvc.perform(mockRequest).andExpect(status().isOk());
    }
}