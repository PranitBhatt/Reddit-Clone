package com.example.RedditClone.mapper;

import com.example.RedditClone.dtos.PostRequestDto;
import com.example.RedditClone.dtos.PostResponseDto;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.models.*;
import com.example.RedditClone.repositories.CommentRepository;
import com.example.RedditClone.repositories.VoteRepository;
import com.example.RedditClone.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import static com.example.RedditClone.models.VoteType.UPVOTE;
import static com.example.RedditClone.models.VoteType.DOWNVOTE;
import java.time.Instant;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PostMapper {

    private CommentRepository commentRepository;
    private UserService userService;
    private VoteRepository voteRepository;
    public PostResponseDto PostDtoFromPost(Post post){
        PostResponseDto responseDto  = new PostResponseDto();
        responseDto.setId(post.getPostId());
        responseDto.setSubredditName(post.getSubreddit().getName());
        responseDto.setUserName(post.getUser().getUserName());
        responseDto.setDescription(post.getContent());
        responseDto.setPostTitle(post.getPostTitle());
        return responseDto;
    }
    public Post map(PostRequestDto postRequestDto, Subreddit subreddit, User user){
        Post post  = new Post();
        post.setContent(postRequestDto.getDescription());
        post.setSubreddit(subreddit);
        post.setUser(user);
        post.setPostTitle(postRequestDto.getPostTitle());
        post.setCreatedAt(Instant.now());
        return post;
    }

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }


//    String getDuration(Post post) {
//        return TimeAgo.using(post.getCreatedAt().toEpochMilli());
//    }

    boolean isPostUpVoted(Post post) throws UserNameNotFoundException {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) throws UserNameNotFoundException {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) throws UserNameNotFoundException {
//        if (userService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            userService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
//        }
//        return false;
    }
}
