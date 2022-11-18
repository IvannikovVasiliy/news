package com.example.news.controller;

import com.example.news.entity.Post;
//import com.example.news.model.PostModel;
import com.example.news.model.PostModel;
import com.example.news.service.AuthorService;
import com.example.news.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class PostsController {

    PostService postService;
    AuthorService authorService;

    @GetMapping
    public List<PostModel> getPosts() {
        List<PostModel> posts = postService.getPosts();

        return posts;
    }

    @GetMapping("/{id}")
    public PostModel infoAboutPost(@PathVariable Long id) {
        PostModel post = postService.getPostById(id);

        return post;
    }

//    @GetMapping("/images/{id}")
//    public ResponseEntity<?> getImage(@PathVariable Long id) {
//        Post post = postService.getPostById(id);
//        return ResponseEntity.ok()
//                .contentType(MediaType.valueOf(post.getContentType().getContentType()))
//                .body(new InputStreamResource(new ByteArrayInputStream(post.getBytes())));
//    }

    //@CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/add")
    public PostModel createPost(@RequestBody PostModel postModel) {
        return postService.createPost(postModel);
    }

    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("path", id+"/edit");
        model.addAttribute("post", new PostModel());
        return "blog-edit";
    }

    @PutMapping("/{id}/edit")
    public PostModel editPost(@PathVariable(value = "id") Long id, @RequestBody PostModel postModel) {
        return postService.editPost(postModel, id);
    }

    @DeleteMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return "POST " + id + " DELETED";
    }

}
