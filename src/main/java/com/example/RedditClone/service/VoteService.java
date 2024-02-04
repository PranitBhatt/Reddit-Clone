package com.example.RedditClone.service;

import com.example.RedditClone.dtos.VoteDto;
import com.example.RedditClone.exceptions.AlreadyVoted;
import com.example.RedditClone.exceptions.PostNotFoundException;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.mapper.VoteMapper;
import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.Vote;
import com.example.RedditClone.repositories.PostRepository;
import com.example.RedditClone.repositories.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.RedditClone.models.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private PostRepository postRepository;
    private VoteRepository voteRepository;
    private UserService userService;
    private VoteMapper voteMapper;

//    @Transactional
    public void vote(VoteDto voteDto) throws PostNotFoundException, AlreadyVoted, UserNameNotFoundException {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + voteDto.getPostId()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,userService.getCurrentUser());

        if(voteByPostAndUser.isPresent() &&
                voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())){
            throw new AlreadyVoted();
        }
        if(UPVOTE.equals(voteDto.getVoteType())){
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(voteMapper.mapVoteDtoToVote(voteDto,post));
        postRepository.save(post);
    }

}
