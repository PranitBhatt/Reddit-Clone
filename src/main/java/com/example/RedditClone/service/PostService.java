package com.example.RedditClone.service;

import com.example.RedditClone.dtos.PostRequestDto;
import com.example.RedditClone.dtos.PostResponseDto;
import com.example.RedditClone.exceptions.PostNotFoundException;
import com.example.RedditClone.exceptions.SubredditNotFoundException;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.mapper.PostMapper;
import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.Subreddit;
import com.example.RedditClone.models.User;
import com.example.RedditClone.repositories.PostRepository;
import com.example.RedditClone.repositories.SubredditRepository;
import com.example.RedditClone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private PostRepository postRepository;
    private PostMapper postMapper;
    private SubredditRepository subredditRepository;
    private UserRepository userRepository;
    private UserService userService;


    public void save(PostRequestDto postRequestDto) throws SubredditNotFoundException, UserNameNotFoundException {
        Subreddit subreddit = subredditRepository.findByName(postRequestDto.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit Not Found With Name:"+ postRequestDto.getSubredditName()));
        postRepository.save(postMapper.map(postRequestDto,subreddit,userService.getCurrentUser()));
    }
    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts(){
        return postRepository.findAll().stream().map(postMapper::PostDtoFromPost).collect(toList());
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long id) throws PostNotFoundException {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + id));
        return postMapper.PostDtoFromPost(post);
    }
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsBySubredditId(Long subredditId) throws SubredditNotFoundException {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException("Subreddit Not Found with ID - " + subredditId));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::PostDtoFromPost).collect(toList());
    }
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostsByUser(String userName) throws UserNameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(()-> new UserNameNotFoundException("Username not found" + userName));
        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(postMapper::PostDtoFromPost).collect(toList());
    }

}
