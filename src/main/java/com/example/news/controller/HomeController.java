package com.example.news.controller;

import com.example.news.entity.Author;
import com.example.news.jwt.JwtTokenProvider;
import com.example.news.model.AuthorModel;
import com.example.news.model.LoginRequest;
        import com.example.news.model.RegistrationModel;
import com.example.news.service.AuthorService;
import com.example.news.service.PostService;
import com.example.news.service.UserService;
        import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
        import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")//(origins = "*")
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

    @GetMapping("/users")
    public List<AuthorModel> findAllUsers() {
        return authorService.findAllUsers();
    }

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody RegistrationModel registrationModel) {
        authorService.addUser(registrationModel);

        return new ResponseEntity("USER CREATED", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword())
        );

        Author author = userService.findByLogin(loginRequest.getLogin());

        return jwtTokenProvider.createToken(loginRequest.getLogin(), author.getRoles());
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

}
