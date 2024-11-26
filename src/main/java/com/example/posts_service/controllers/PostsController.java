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

    // ======================================================
    // ================  Public Endpoints  ===============
    // ======================================================

    /**
     * Gives the posts data paginated.
     *
     * @param page Integer indicating the number of page
     * @return ResponseEntity containing the data from the posts
     */
    @SuppressWarnings("rawtypes")
    @GetMapping(value = "/page/{page}")
    public ResponseEntity getPostsPaginated(@PathVariable int page){
        
        return postService.getPosts(page);

    }

    /**
     * Gives the posts data from an user paginated.
     *
     * @param page Integer indicating the number of page
     * @param userId String indicating user's Id
     * @return ResponseEntity containing the data from the posts
     */
    @SuppressWarnings("rawtypes")
    @GetMapping(value = "/page/{page}/{userId}")
    public ResponseEntity getPostsPaginated(@PathVariable int page, @PathVariable String userId){

        return postService.getPostsFromUser(userId, page);
        
    }

    // ======================================================
    // ================  USER Role Endpoints  ===============
    // ======================================================

    /**
     * Creates a post and saves it.
     *
     * @param data Object containing all the data from the post to save
     * @param result Object checking the validation from the post data
     * @param token String containing the token from the user interacting
     * @return ResponseEntity containing the data from the posts
     */
    @SuppressWarnings("rawtypes")
    @PostMapping(value = "/add-post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPost(@Valid @RequestBody AddPostDTO data, BindingResult result, 
                                    @RequestHeader("Authorization") String token){

        if (result.hasErrors()){
            String error = result.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(error);
        }
        
        String userId = tokenService.getIdFromToken(token);

        return postService.createPost(userId, data.songName(), data.songAuthor(), data.songReview());
    }

    /**
     * Likes or unlikes a post.
     *
     * @param postId String containing the post id to be liked/unliked
     * @param token String containing the token from the user interacting
     * @return ResponseEntity indicating success or failure of the like/unlike
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/{postId}/like")
    public ResponseEntity likePost(@PathVariable String postId, @RequestHeader("Authorization") String token){

        String userId = tokenService.getIdFromToken(token);

        return postService.like(postId, userId);
    }

    /**
     * Deletes a post.
     *
     * @param postId String containing the post id to be deleted
     * @param token String containing the token from the user interacting
     * @return ResponseEntity indicating success or failure of deleting a post
     */
    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{postId}/delete")
    public ResponseEntity deletePost(@PathVariable String postId, @RequestHeader("Authorization") String token){

        String userId = tokenService.getIdFromToken(token);

        return postService.deletePost(postId, userId);
    }

    // ======================================================
    // ===============  ADMIN Role Endpoints  ===============
    // ======================================================

    /**
     * Removes a post.
     *
     * @param postId String containing the post id to be deleted
     * @return ResponseEntity indicating success or failure of removing a post
     */
    @SuppressWarnings("rawtypes")
    @DeleteMapping("/{postId}/remove")
    public ResponseEntity deleteAnyPost(@PathVariable String postId){

        return postService.deleteAnyPost(postId);
    }
}

