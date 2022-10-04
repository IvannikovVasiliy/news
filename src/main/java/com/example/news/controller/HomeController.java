package com.example.news.controller;

import com.example.news.entity.Author;
import com.example.news.model.RegistrationModel;
import com.example.news.service.AuthorService;
import com.example.news.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class HomeController {

    AuthorService authorService;
    PostService postService;

    @GetMapping("/home")
    public String home(Model model) {
        System.out.println(postService.getLast2InPast().size());
        model.addAttribute("postsPast", postService.getLast2InPast());
        model.addAttribute("postsFuture", postService.getLast3InFuture());
        return "home";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("author", new Author());
        return "registration";
    }

    @PostMapping("/registration")
    public String addAuthor(@RequestBody RegistrationModel regModel) {
        authorService.addUser(regModel);

        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("author", new Author());
        return "profile";
    }

    @GetMapping("/login")
    public String getLogin(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("login", logout != null);

        return "login";

    }

}
