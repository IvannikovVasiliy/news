package com.example.news.controller;

import com.example.news.entity.Author;
import com.example.news.entity.Post;
import com.example.news.model.PostModel;
import com.example.news.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class PostsControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        openMocks(this); //init
        mockMvc = MockMvcBuilders.standaloneSetup(postsController).build();
    }

    @Mock
    private PostService postService;

    @InjectMocks
    private PostsController postsController;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    Post post = new Post(1L, "1", "1", "1", new Author());
    PostModel postModel = new PostModel("1", "1", "1");

    @Test
    public void getAllPosts() throws Exception {
        List<PostModel> posts = List.of(postModel);
        when(postService.getPosts()).thenReturn(posts);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("1")));
    }

    @Test
    public void getPostById() throws Exception {
        when(postService.getPostById(post.getId())).thenReturn(postModel);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("1")));
    }

    @Test
    public void createPost() throws Exception {
        when(postService.createPost(postModel)).thenReturn(postModel);

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.post("/posts/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectWriter.writeValueAsString(postModel));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("1")));

//        mockMvc.perform(post("/posts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectWriter.writeValueAsString(postModel)))
//                .andExpect(status().isOk());
    }

    @Test
    public void updatePost() throws Exception {
        when(postService.editPost(postModel, 1L)).thenReturn(postModel);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/posts/1/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectWriter.writeValueAsString(postModel));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void deletePost() throws Exception {
        when(postService.deleteById(1L)).thenReturn(true);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/posts/1/delete")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest).andExpect(status().isOk());
    }
}