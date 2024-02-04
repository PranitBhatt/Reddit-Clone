package com.example.RedditClone.mapper;

import com.example.RedditClone.dtos.VoteDto;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.Vote;
import com.example.RedditClone.repositories.UserRepository;
import com.example.RedditClone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class VoteMapper {

    private UserService userService;
    public Vote mapVoteDtoToVote(VoteDto voteDto, Post post) throws UserNameNotFoundException {
        Vote vote = new Vote();
        vote.setVoteType(voteDto.getVoteType());
        vote.setPost(post);
        vote.setUser(userService.getCurrentUser());
        return vote;
    }
}
