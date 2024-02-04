package com.example.RedditClone.repositories;

import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.Subreddit;
import com.example.RedditClone.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
