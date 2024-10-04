package com.example.posts_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

import com.example.posts_service.entities.posts.Post;
import com.example.posts_service.repositories.PostRepository;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;

    @SuppressWarnings("rawtypes")
    public ResponseEntity createPost(String userId, String songName, String songAuthor, String songReview){
        try{
            Post post = new Post();
            post.setUserId(userId);
            post.setSongName(songName);
            post.setSongAuthor(songAuthor);
            post.setSongReview(songReview);
            post.setTimestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now().minus(Duration.ofHours(6))));
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity likePost(String postId, String userId) {
        try {
            Optional<Post> optionalPost = postRepository.findById(postId);
            
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                
                if (!post.getLikes().contains(userId)) {
                    post.addLike(userId);
                    postRepository.save(post);
                    return ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity unlikePost(String postId, String userId) {
        try {
            Optional<Post> optionalPost = postRepository.findById(postId);
            
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();
                
                if (post.getLikes().contains(userId)) {
                    post.removeLike(userId);
                    postRepository.save(post);
                    return ResponseEntity.status(HttpStatus.OK).build();
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity deletePost(String postId, String userId){
        ResponseEntity<Post> response = findPostById(postId);
        Post post = response.getBody();

        if(post.getUserId().equals(userId)){
            postRepository.delete(post);
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    private ResponseEntity<Post> findPostById(String postId) {
        try{
            Optional<Post> optionalPost = postRepository.findById(postId);
        
            if (optionalPost.isPresent()) {
                return ResponseEntity.ok(optionalPost.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
