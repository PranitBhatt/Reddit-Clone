package com.example.RedditClone.contoller;

import com.example.RedditClone.dtos.CommentsDto;
import com.example.RedditClone.exceptions.PostNotFoundException;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.mapper.CommentMapper;
import com.example.RedditClone.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/reddit/comments")
@AllArgsConstructor
public class CommentsController {

    private CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<CommentsDto> createComment(@RequestBody CommentsDto commentsDto) throws PostNotFoundException, UserNameNotFoundException {
        CommentsDto commentsDto1 = commentService.save(commentsDto);
        return ResponseEntity.status(CREATED).body(commentsDto1);
    }

    @GetMapping("/fromPost")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@RequestParam Long postId) throws PostNotFoundException {
        List<CommentsDto> commentsDto = commentService.getAllCommentsFromPost(postId);
        return ResponseEntity.status(OK).body(commentsDto);
    }

    @GetMapping("/fromUsername")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForUser(@RequestParam String userName) throws UserNameNotFoundException {
        List<CommentsDto> commentsDto = commentService.getAllCommentsOfUser(userName);
        return ResponseEntity.status(OK).body(commentsDto);
    }
}
