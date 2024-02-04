package com.example.RedditClone.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private Long postId;
    private String subredditName;
    @NotBlank(message = "Post Name cannot be empty or Null")
    private String postTitle;
//    private String url;
    private String description;
}
