package com.example.posts_service.entities.posts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Document(collection = "posts")
public class Post {
    
    @Id
    private String id;

    private String userId;
    private String timestamp;
    private String songName;
    private String songAuthor;
    private String songReview;
    private List<String> likes = new ArrayList<>();;

    public void addLike(String userId) {
        if (!this.likes.contains(userId)) {
            this.likes.add(userId);
        }
    }

    public void removeLike(String userId) {
        this.likes.remove(userId);
    }

}
