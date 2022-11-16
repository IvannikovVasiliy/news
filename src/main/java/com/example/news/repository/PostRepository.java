package com.example.news.repository;

import com.example.news.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select * from post where news_type_id = 1 order by id desc limit 3;", nativeQuery = true)
    List<Post> findLast3InFuture();

    @Query(value = "SELECT * FROM post where news_type_id = 2 order by id desc limit 2;", nativeQuery = true)
    List<Post> findLast2InPast();
}
