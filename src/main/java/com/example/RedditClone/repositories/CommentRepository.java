package com.example.RedditClone.repositories;

import com.example.RedditClone.dtos.CommentsDto;
import com.example.RedditClone.models.Comment;
import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
