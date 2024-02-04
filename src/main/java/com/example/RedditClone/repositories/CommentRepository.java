package com.example.RedditClone.repositories;

import com.example.RedditClone.dtos.CommentsDto;
import com.example.RedditClone.models.Comment;
import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
