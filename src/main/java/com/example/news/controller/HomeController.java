package com.example.news.controller;

import com.example.news.entity.Author;
//import com.example.news.model.LoginRequest;
//import com.example.news.model.RegistrationModel;
import com.example.news.model.RegistrationModel;
import com.example.news.service.AuthorService;
import com.example.news.service.PostService;
import com.example.news.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class HomeController {

    UserService userService;
    AuthorService authorService;
    PostService postService;

    @GetMapping("/")
    public String home() {

        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("regModel", new RegistrationModel());
        return "registration";
    }

    @PostMapping("/registration")
    public String addAuthor(RegistrationModel registrationModel) {
        authorService.addUser(registrationModel);

        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        return "profile";
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);

        return "login";
    }

}
