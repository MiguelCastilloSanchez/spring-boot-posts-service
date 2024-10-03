package com.example.posts_service.entities.posts.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddPostDTO(

    @NotBlank
    @Size(max = 40)
    String songName,

    @NotBlank
    @Size(max = 40)
    String songAuthor,

    @NotBlank
    @Size(max = 300)
    String songReview 
){
}
