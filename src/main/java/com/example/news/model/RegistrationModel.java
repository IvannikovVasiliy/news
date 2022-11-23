package com.example.news.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationModel {
    private String email;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String role;
}
