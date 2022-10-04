package com.example.news.service;

import com.example.news.entity.Author;
import com.example.news.entity.Role;
import com.example.news.model.RegistrationModel;
import com.example.news.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthorService implements UserDetailsService {

    AuthorRepository authorRepository;

    public List<Author> getAuthor() {
        return authorRepository.findAll();
    }

    public Author findByUsername(String name) {
        return authorRepository.findByLogin(name);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Author author = findByUsername(name);

        return new User(author.getLogin(), author.getPassword(), mapRoles(author.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRoles(Collection<Role> roles) {
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    public void addUser(RegistrationModel regModel) {
        Author author;

    }
}
