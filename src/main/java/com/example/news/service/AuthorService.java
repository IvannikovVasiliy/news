package com.example.news.service;


import com.example.news.entity.Author;
import com.example.news.entity.ERole;
import com.example.news.model.RegistrationModel;
import com.example.news.repository.AuthorRepository;
import com.example.news.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public Author findByUsername(String name) {
        return authorRepository.findByLogin(name);
    }

    public void addUser(RegistrationModel regModel) {
        Author author = new Author(
                regModel.getEmail(),
                regModel.getLogin(),
                passwordEncoder.encode(regModel.getPassword()),
                regModel.getName(),
                regModel.getSurname(),
                Arrays.asList(roleRepository.findByName(ERole.ROLE_USER))
        );

        authorRepository.save(author);
    }
}
