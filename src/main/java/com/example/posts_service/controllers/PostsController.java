package com.example.posts_service.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.posts_service.entities.posts.dtos.AddPostDTO;
import com.example.posts_service.services.PostService;
import com.example.posts_service.services.TokenService;

import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/posts", produces = {"application/json"})
public class PostsController {

    @Autowired
    private PostService postService;

    @Autowired
    private TokenService tokenService;

    @SuppressWarnings("rawtypes")
    @GetMapping(value = "/test-admin")
    public ResponseEntity test(){
        return ResponseEntity.ok("Hello, Admin!");
    }

    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/add-post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPost(@Valid @RequestBody AddPostDTO data, BindingResult result, 
                                    @RequestHeader("Authorization") String token){

        if (result.hasErrors()) return ResponseEntity.badRequest().build();
        
        String userId = tokenService.getIdFromToken(token);

        return postService.createPost(userId, data.songName(), data.songAuthor(), data.songReview());
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/{postId}/like")
    public ResponseEntity likePost(@PathVariable String postId, @RequestHeader("Authorization") String token){

        String userId = tokenService.getIdFromToken(token);

        return postService.like(postId, userId);
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{postId}/delete")
    public ResponseEntity deletePost(@PathVariable String postId, @RequestHeader("Authorization") String token){

        String userId = tokenService.getIdFromToken(token);

        return postService.deletePost(postId, userId);
    }

}

