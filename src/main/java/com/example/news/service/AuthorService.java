package com.example.news.service;


import com.example.news.entity.Author;
import com.example.news.model.RegistrationModel;
import com.example.news.repository.AuthorRepository;
import com.example.news.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthorRepository authorRepository;

    public List<Author> getAuthor() {
        return authorRepository.findAll();
    }

    public Author findByLogin(String login) {
        return authorRepository.findByLogin(login);
    }

    public RegistrationModel addUser(RegistrationModel regModel) {
        Author author = new Author(
                regModel.getEmail(),
                regModel.getLogin(),
                passwordEncoder.encode(regModel.getPassword()),
                regModel.getName(),
                regModel.getSurname(),
                Arrays.asList(roleRepository.findByName(regModel.getRole()))
        );

        authorRepository.save(author);

        return regModel;
    }
}
