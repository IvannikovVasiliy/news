package com.example.news.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostModel {
    private String title;
    private String previewText;
    private String fullText;
    private String newsType;
}
