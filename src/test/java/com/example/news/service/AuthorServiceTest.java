package com.example.news.service;

import com.example.news.config.WebSecurityConfig;
import com.example.news.entity.Author;
import com.example.news.entity.Role;
import com.example.news.model.RegistrationModel;
import com.example.news.repository.AuthorRepository;
import com.example.news.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    public WebSecurityConfig webSecurityConfig;
    @InjectMocks
    private AuthorService authorService;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    Author author = new Author(
            "ivanov@gmail.com",
            "ivanov",
            "ivanov",
            "ivan",
            "ivanov",
            List.of(new Role("ROLE_ADMIN"))
    );

    @Test
    public void addUserTest() {
        when(authorRepository.save(any())).thenReturn(author);
        when(webSecurityConfig.bCryptPasswordEncoder()).thenReturn(new BCryptPasswordEncoder());

        RegistrationModel registrationModel = RegistrationModel.builder()
                .email("ivanov@gmail.com")
                .login("ivanov")
                .password("ivanov")
                .name("ivan")
                .surname("ivanov")
                .role("ROLE_USER")
                .build();
        assertTrue(authorService.addUser(registrationModel));
    }

    @Test
    public void getAuthorTest() {
        when(authorRepository.findAll()).thenReturn(List.of(author));
        List<Author> authors = authorService.getAllAuthors();
        assertEquals(authors, List.of(author));
    }

    @Test
    public void findByLogin() {
        when(authorRepository.findByLogin(any())).thenReturn(author);
        Author authorRes = authorService.findByLogin("author");
        assertEquals(author, authorRes);
    }
}
