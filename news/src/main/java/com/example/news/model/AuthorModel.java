package com.example.news.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class AuthorModel {
    public String login;
    public List<String> roles;
}
