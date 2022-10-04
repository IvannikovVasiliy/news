package com.example.news.repository;

import com.example.news.entity.NewsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTypeRepository extends JpaRepository<NewsType, Long> {
    NewsType findByTypeNews(String type);
}
