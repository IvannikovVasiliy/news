package com.example.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NewsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }

}
