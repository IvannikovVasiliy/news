package com.example.news.service;

import com.example.news.entity.Author;
import com.example.news.entity.Post;
import com.example.news.entity.Role;
import com.example.news.jwt.JwtTokenProvider;
import com.example.news.model.PostModel;
import com.example.news.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostServiceSpringBootTest {

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    Author author = new Author(
            "ivanov@gmail.com",
            "ivanov",
            "ivanov",
            "ivan",
            "ivanov",
            List.of(new Role("ROLE_ADMIN"))
    );
    Post post = new Post("Расписание", "Расписание занятий", "Расписание занятий факультета ПММ", author);
    PostModel postModel = PostModel.builder()
            .title("Расписание")
            .previewText("Расписание занятий")
            .fullText("Расписание занятий факультета ПММ")
            .build();

    @Test
    public void getPosts() {
        List<Post> posts = List.of(post);
        when(postRepository.findAll()).thenReturn(posts);
        List<PostModel> postModels = postService.getPosts();
        assertEquals(postModels.size(), 1);
        assertEquals(postModels.get(0).title, posts.get(0).getTitle());
    }

    @Test
    public void getPostByIdTest() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        Post postResult = postRepository.findById(37L).get();
        assertEquals(postResult.getTitle(), post.getTitle());
    }

    @Test
    public void createPost() {
        String token = jwtTokenProvider.createToken("1", List.of(new Role("ROLE_USER")));
        Authentication authentication = jwtTokenProvider.getAuth(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        when(postRepository.save(any())).thenReturn(post);
        PostModel postModelResult = postService.createPost(postModel);
        assertEquals(postModel, postModelResult);
    }

    @Test
    public void editPostTest() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        PostModel postModelRes = postService.editPost(postModel, 1L);
        assertEquals(postModelRes.title, postModel.title);
        assertEquals(postModelRes.getPreviewText(), postModel.getPreviewText());
        assertEquals(postModelRes.getFullText(), postModel.getFullText());
    }
}