package com.example.news.controller;

import com.example.news.entity.Author;
//import com.example.news.model.LoginRequest;
//import com.example.news.model.RegistrationModel;
import com.example.news.jwt.JwtTokenProvider;
import com.example.news.model.LoginRequest;
import com.example.news.model.RegistrationModel;
import com.example.news.service.AuthorService;
import com.example.news.service.PostService;
import com.example.news.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final AuthorService authorService;
    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/registration")
    public String registration(RegistrationModel registation) {
        return "USER CREATED";
    }

    @PostMapping("/registration")
    public String addAuthor(@RequestBody RegistrationModel registrationModel) {
        authorService.addUser(registrationModel);

        return "USER CREATED";
    }

    @PostMapping("/signin")
    public String login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        Author author = userService.findByUsername(loginRequest.getUsername());

        return jwtTokenProvider.createToken(loginRequest.getUsername(), author.getRoles());
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

}
