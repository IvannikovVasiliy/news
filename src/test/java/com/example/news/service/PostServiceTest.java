package com.example.news.service;

import com.example.news.entity.Author;
import com.example.news.entity.Post;
import com.example.news.entity.Role;
import com.example.news.model.PostModel;
import com.example.news.repository.PostRepository;
import com.example.news.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceTest {
    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Test
    public void getPosts() {
        Author author = new Author(
                "ivanov@gmail.com",
                "ivanov",
                "ivanov",
                "ivan",
                "ivanov",
                List.of(new Role("ROLE_ADMIN"))
        );
        Post post = new Post("Расписание", "Расписание занятий", "Расписание занятий факультета ПММ", author);

        List<Post> posts = List.of(post);
        when(postRepository.findAll()).thenReturn(posts);
        List<PostModel> postModels = postService.getPosts();
        assertEquals(postModels.size(), 1);
        assertEquals(postModels.get(0).title, posts.get(0).getTitle());
    }
}