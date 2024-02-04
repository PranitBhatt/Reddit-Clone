package com.example.RedditClone.dtos;

import com.example.RedditClone.models.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
