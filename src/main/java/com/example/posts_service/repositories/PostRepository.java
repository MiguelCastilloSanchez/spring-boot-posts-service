package com.example.posts_service.repositories;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.posts_service.entities.posts.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
    Optional<Post> findByIdAndLikesContains(String postId, String userId);
}
