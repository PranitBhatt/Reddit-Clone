package com.example.RedditClone.service;

import com.example.RedditClone.dtos.CommentsDto;
import com.example.RedditClone.exceptions.PostNotFoundException;
import com.example.RedditClone.exceptions.UserAlreadyExists;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.mapper.CommentMapper;
import com.example.RedditClone.models.Comment;
import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.User;
import com.example.RedditClone.repositories.CommentRepository;
import com.example.RedditClone.repositories.PostRepository;
import com.example.RedditClone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {

    private PostRepository postRepository;
    private CommentRepository commentRepository;
    private CommentMapper commentMapper;
    private UserService userService;
    private UserRepository userRepository;
    public CommentsDto save(CommentsDto commentsDto) throws PostNotFoundException, UserNameNotFoundException {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto,post,userService.getCurrentUser());
        commentRepository.save(comment);
        return commentMapper.CommentsDtofromComment(comment);
//        return null;
    }


    public List<CommentsDto> getAllCommentsFromPost(Long postId) throws PostNotFoundException {
        Post post  = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post Not Found with ID - " + postId));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::CommentsDtofromComment)
                .toList();
    }

    public List<CommentsDto> getAllCommentsOfUser(String userName) throws UserNameNotFoundException {
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNameNotFoundException("Username not found" + userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::CommentsDtofromComment)
                .toList();
    }
}
