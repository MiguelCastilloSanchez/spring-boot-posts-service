package com.example.posts_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.posts_service.entities.posts.Post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface PostRepository extends MongoRepository<Post, String>{
    void deleteByUserId(String userId);
    Page<Post> findByUserId(String userId, Pageable pageable);
}
