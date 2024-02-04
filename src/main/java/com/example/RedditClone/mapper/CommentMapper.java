package com.example.RedditClone.mapper;

import com.example.RedditClone.dtos.CommentsDto;
import com.example.RedditClone.dtos.PostResponseDto;
import com.example.RedditClone.models.Comment;
import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.User;
import com.example.RedditClone.service.CommentService;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
public class CommentMapper {
    public Comment map(CommentsDto commentsDto, Post post, User user){
        Comment comment = new Comment();
        comment.setContent(commentsDto.getText());
        comment.setCreatedDate(Instant.now());
        comment.setPost(post);
        comment.setUser(user);
        return comment;
    }

    public CommentsDto CommentsDtofromComment(Comment comment){
        CommentsDto responseDto  = new CommentsDto();
        responseDto.setPostId(comment.getPost().getPostId());
        responseDto.setUserName(comment.getUser().getUserName());
        responseDto.setText(comment.getContent());
        responseDto.setCreatedDate(Instant.now());
        responseDto.setId(comment.getCommentId());
        return responseDto;
    }

}
