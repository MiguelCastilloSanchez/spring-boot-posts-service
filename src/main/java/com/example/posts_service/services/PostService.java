package com.example.posts_service.services;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.posts_service.entities.posts.Post;
import com.example.posts_service.repositories.PostRepository;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;

    public ResponseEntity<Page<Post>>getPosts(int page){
        int size = 5;
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postRepository.findAll(pageable);
        return ResponseEntity.ok(postsPage);
    }

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
    public ResponseEntity like(String postId, String userId) {

        ResponseEntity<Post> response = findPostById(postId);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        Post post = response.getBody();

        if (!post.getLikes().contains(userId)) {
            post.addLike(userId);
            postRepository.save(post);
        } else {
            post.removeLike(userId);
            postRepository.save(post);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity deletePost(String postId, String userId){
        
        ResponseEntity<Post> response = findPostById(postId);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        
        Post post = response.getBody();

        if(post.getUserId().equals(userId)){
            postRepository.delete(post);
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity deleteAnyPost(String postId){
        
        ResponseEntity<Post> response = findPostById(postId);
        if (!response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        
        Post post = response.getBody();

        postRepository.delete(post);
        return ResponseEntity.status(HttpStatus.OK).build();

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
