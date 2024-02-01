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
                Author author) {
        this.title = title;
        this.previewText = previewText;
        this.fullText = fullText;
        this.author = author;
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

    @ManyToOne
    @JoinColumn(name = "id_author")
    private Author author;

}
