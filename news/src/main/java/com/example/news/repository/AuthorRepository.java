package com.example.news.repository;

import com.example.news.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByLogin(String login);
}
