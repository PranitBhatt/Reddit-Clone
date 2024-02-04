package com.example.RedditClone.mapper;

import com.example.RedditClone.dtos.SubredditDto;
import com.example.RedditClone.models.Post;
import com.example.RedditClone.models.Subreddit;
import com.example.RedditClone.models.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubredditMapper {

    public Subreddit mapSubredditDtoToSubreddit(SubredditDto subredditDto , User user){
        Subreddit mappedSubreddit = new Subreddit();
//        mappedSubreddit.setSubredditId(subredditDto.getSubredditId());
        mappedSubreddit.setName(subredditDto.getName());
        mappedSubreddit.setDescription(subredditDto.getDescription());
        mappedSubreddit.setUser(user);
        return mappedSubreddit;
    }
     public Integer mapPosts(List<Post> numberOfPosts) {
        if(numberOfPosts!=null){
            return numberOfPosts.size();
        }
        else {
            return 0;
        }
    }
    public SubredditDto mapSubredditToSubredditDto(Subreddit subreddit){
        SubredditDto mappedSubredditDto = new SubredditDto();
        mappedSubredditDto.setSubredditId(subreddit.getSubredditId());
        mappedSubredditDto.setName(subreddit.getName());
        mappedSubredditDto.setDescription(subreddit.getDescription());
//        mappedSubredditDto.setNumberOfPosts(mapPosts(subreddit.getPosts()));
        mappedSubredditDto.setNumberOfPosts(mapPosts(subreddit.getPosts()));
        return mappedSubredditDto;
    }
}
