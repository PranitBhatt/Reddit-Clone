package com.example.RedditClone.contoller;


import com.example.RedditClone.dtos.VoteDto;
import com.example.RedditClone.exceptions.AlreadyVoted;
import com.example.RedditClone.exceptions.PostNotFoundException;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reddit/votes")
@AllArgsConstructor
public class VoteController {

    private VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) throws PostNotFoundException, AlreadyVoted, UserNameNotFoundException {
        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
