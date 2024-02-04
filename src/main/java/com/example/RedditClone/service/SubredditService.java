package com.example.RedditClone.service;

import com.example.RedditClone.dtos.CustomUserDetail;
import com.example.RedditClone.dtos.SubredditDto;
//import com.example.RedditClone.dtos.UserDetail;
import com.example.RedditClone.exceptions.SubredditDoesNotExists;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.mapper.SubredditMapper;
import com.example.RedditClone.models.Subreddit;
import com.example.RedditClone.models.User;
import com.example.RedditClone.repositories.SubredditRepository;
import com.example.RedditClone.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final SubredditMapper subredditMapper;
    private final UserService userService;
    private UserRepository userRepository;

    public SubredditService(SubredditRepository subredditRepository, SubredditMapper subredditMapper, UserService userService) {
        this.subredditRepository = subredditRepository;
        this.subredditMapper = subredditMapper;
        this.userService = userService;
    }

    @Transactional
    public SubredditDto createSubreddit(SubredditDto subredditDto) throws UserNameNotFoundException {
        Subreddit saved = subredditRepository.save(subredditMapper.mapSubredditDtoToSubreddit(subredditDto,userService.getCurrentUser()));
        SubredditDto responseDto = subredditMapper.mapSubredditToSubredditDto(saved);
        return responseDto;
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(subredditMapper::mapSubredditToSubredditDto)
                .collect(toList());
    }

    public SubredditDto getSubredditById(Long id) throws SubredditDoesNotExists {
        Subreddit subreddit = subredditRepository.findById(id)
                .orElseThrow(() -> new SubredditDoesNotExists("Subreddit Not Found with ID - " + id));
        return subredditMapper.mapSubredditToSubredditDto(subreddit);
    }

}
