package com.example.news.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    public Post(String title,
                String previewText,
                String fullText,
                Author author,
                NewsType newsType,
                String imageName) {
        this.title = title;
        this.previewText = previewText;
        this.fullText = fullText;
        this.author = author;
        this.newsType = newsType;
        this.imageName = imageName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Column(name = "preview_text")
    private String previewText;

    @Column(length = 8192, name = "full_text")
    @NotNull
    private String fullText;

    @Column(name = "image_name")
    private String imageName;

    @ManyToOne
    private Author author;

    @ManyToOne
    @JoinColumn(name = "news_type_id")
    private NewsType newsType;

}
