package com.example.news.service;

import com.example.news.entity.Author;
import com.example.news.entity.Role;
import com.example.news.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Author author = authorRepository.findByLogin(login);

        return new User(author.getLogin(), author.getPassword(), mappedRoles(author.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mappedRoles(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> resultSet = new ArrayList<>();
        roles.stream().forEach(role -> resultSet.add(
                new SimpleGrantedAuthority(role.getName()))
        );

        return resultSet;
    }

    public Author findByLogin(String login) {

        return authorRepository.findByLogin(login);
    }
}