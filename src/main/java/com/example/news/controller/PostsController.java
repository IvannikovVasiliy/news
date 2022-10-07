package com.example.news.controller;

import com.example.news.entity.Post;
//import com.example.news.model.PostModel;
import com.example.news.model.PostModel;
import com.example.news.service.AuthorService;
import com.example.news.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
public class PostsController {

    PostService postService;
    AuthorService authorService;


    @GetMapping
    public String getPosts(Model model) {
        List<Post> posts = postService.getPosts();
        model.addAttribute("posts", posts);

        return "blog";
    }

    @GetMapping("/{id}")
    public String infoAboutPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "blog-details";
    }

//    @GetMapping("/images/{id}")
//    public ResponseEntity<?> getImage(@PathVariable Long id) {
//        Post post = postService.getPostById(id);
//        return ResponseEntity.ok()
//                .contentType(MediaType.valueOf(post.getContentType().getContentType()))
//                .body(new InputStreamResource(new ByteArrayInputStream(post.getBytes())));
//    }

    @GetMapping("/add")
    public String createPost(Model model) {
        model.addAttribute("path", "add");
        model.addAttribute("post", new PostModel());
        return "blog-add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String createPost(Model model,
                             @ModelAttribute PostModel postModel,
                             @RequestParam MultipartFile image) throws IOException {
        postService.createPost(postModel, image);

        return "redirect:/posts";
    }

    @GetMapping("/{id}/edit")
    public String editPost(@PathVariable(value = "id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("path", id+"/edit");
        model.addAttribute("post", new Post());
        return "blog-add";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable(value = "id") Integer id,
                           @ModelAttribute PostModel postModel,
                           @RequestParam MultipartFile image,
                           Model model) throws IOException {

        postService.createPost(postModel, image);
        return "redirect:/posts";
    }

    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postService.deleteById(id);
        return "redirect:/posts";
    }

}
