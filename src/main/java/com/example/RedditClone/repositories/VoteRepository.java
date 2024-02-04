package com.example.RedditClone.repositories;

import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.User;
import com.example.RedditClone.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
