package com.example.RedditClone.contoller;

import com.example.RedditClone.dtos.CustomUserDetail;
import com.example.RedditClone.dtos.SubredditDto;
//import com.example.RedditClone.dtos.UserDetail;
import com.example.RedditClone.exceptions.SubredditDoesNotExists;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.models.Subreddit;
import com.example.RedditClone.models.User;
import com.example.RedditClone.service.SubredditService;
import com.fasterxml.jackson.databind.jsontype.SubtypeResolver;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/reddit/subreddit")
@AllArgsConstructor
public class SubredditController {

    private SubredditService subredditService;

    @PostMapping("/create")
    public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) throws UserNameNotFoundException {
        SubredditDto response = subredditService.createSubreddit(subredditDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubredditDto>> getAllSubreddit(){
        return ResponseEntity.status(OK).body(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubredditDto> getSubredditById(@PathVariable Long id) throws SubredditDoesNotExists {
        return ResponseEntity.status(OK).body(subredditService.getSubredditById(id));
    }

//    @GetMapping("/currentUser")
//    public ResponseEntity<User> getCurrentUser(HttpSession session) throws UserNameNotFoundException {
//        User ans = subredditService.getCurrentUser();
//        session.
//        return new ResponseEntity<>(ans,OK);
//    }
}
