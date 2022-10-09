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

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
//@AllArgsConstructor
public class PostService {

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    NewsTypeRepository newsRepository;

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {
        //Ошибка
        return postRepository.findById(id).orElseThrow();
    }

    public Post createPost(PostModel postModel/*, MultipartFile image*/) throws IOException {
//        String imageName = UUID.randomUUID().toString() + image.getOriginalFilename();
//        System.out.println(imageName);
//        try (FileOutputStream fos = new FileOutputStream(
//                "D://Spring//news//src//main//resources//static//" + imageName
//        )) {
//            // перевод строки в байты
//            byte[] buffer = image.getBytes();
//
//            fos.write(buffer, 0, buffer.length);
//            System.out.println("The file has been written");
//        }
//        catch(IOException ex){
//
//            System.out.println(ex.getMessage());
//        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Author author = authorRepository.findByLogin(authentication.getName());
        System.out.println(author.getId());
        System.out.println(author.getEmail());

        Post post = new Post(
                postModel.getTitle(),
                postModel.getPreviewText(),
                postModel.getFullText(),
                author
        );

        return postRepository.save(post);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public List<Post> getLast3InFuture() {
        return postRepository.findLast3InFuture();
    }

    public List<Post> getLast2InPast() {
        return postRepository.findLast2InPast();
    }

    public void editPost(PostModel postModel, Long id) {
        Post post = postRepository.findById(id).get();
        post.setTitle(postModel.getTitle());
        post.setPreviewText(post.getPreviewText());
        post.setFullText(post.getFullText());

        postRepository.save(post);
    }
}