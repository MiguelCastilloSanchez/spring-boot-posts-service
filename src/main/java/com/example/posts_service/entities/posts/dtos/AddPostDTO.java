package com.example.posts_service.entities.posts.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddPostDTO(

    @NotBlank
    @Size(max = 40, message = "Song name must be at maximum 40 characters")
    String songName,

    @NotBlank
    @Size(max = 40, message = "Author name must be at maximum 40 characters")
    String songAuthor,

    @NotBlank
    @Size(max = 300, message = "Review must be at maximum 300 characters")
    String songReview 
){
}
