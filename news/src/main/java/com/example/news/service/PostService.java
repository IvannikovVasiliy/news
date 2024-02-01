package com.example.news.service;


import com.example.news.entity.Author;
import com.example.news.entity.Post;
import com.example.news.model.PostModel;
import com.example.news.repository.AuthorRepository;
import com.example.news.repository.NewsTypeRepository;
import com.example.news.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final NewsTypeRepository newsRepository;

    public List<PostModel> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> PostModel.builder()
                        .title(post.getTitle())
                        .previewText(post.getPreviewText())
                        .fullText(post.getFullText())
                        .build()
                )
                .toList();
    }

    public PostModel getPostById(Long id) {
        Post post = postRepository.findById(id).get();

        return PostModel.builder().title(post.getTitle()).build();
    }

    public PostModel createPost(PostModel postModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Author author = authorRepository.findByLogin(authentication.getName());

        Post post = new Post(
                postModel.getTitle(),
                postModel.getPreviewText(),
                postModel.getFullText(),
                author
        );
        postRepository.save(post);

        return PostModel
                .builder()
                .title(post.getTitle())
                .previewText(post.getPreviewText())
                .fullText(post.getFullText())
                .build();
    }

    public boolean deleteById(Long id) {
        postRepository.deleteById(id);
        return true;
    }

    public PostModel editPost(PostModel postModel, Long id) {
        Post post = postRepository.findById(id).get();
        post.setTitle(postModel.getTitle());
        post.setPreviewText(post.getPreviewText());
        post.setFullText(post.getFullText());

        Post res = postRepository.save(post);

        return PostModel.builder()
                .title(post.getTitle())
                .previewText(post.getPreviewText())
                .fullText(post.getFullText())
                .build();

    }
}