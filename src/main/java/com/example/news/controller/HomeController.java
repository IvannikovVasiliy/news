package com.example.news.controller;

//import com.example.news.model.LoginRequest;
//import com.example.news.model.RegistrationModel;
import com.example.news.entity.Author;
import com.example.news.jwt.JwtTokenProvider;
import com.example.news.model.LoginRequest;
        import com.example.news.model.RegistrationModel;
import com.example.news.service.AuthorService;
import com.example.news.service.PostService;
import com.example.news.service.UserService;
        import lombok.RequiredArgsConstructor;
        import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
        import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/registration")
    public String addAuthor(@RequestBody RegistrationModel registrationModel) {
        authorService.addUser(registrationModel);

        return "USER CREATED";
    }

    @PostMapping("/signin")
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
