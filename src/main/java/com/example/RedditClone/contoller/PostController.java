package com.example.RedditClone.contoller;

import com.example.RedditClone.dtos.PostRequestDto;
import com.example.RedditClone.dtos.PostResponseDto;
import com.example.RedditClone.exceptions.PostNotFoundException;
import com.example.RedditClone.exceptions.SubredditNotFoundException;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reddit/post")
@AllArgsConstructor
public class PostController {

    private PostService postService;

    @PostMapping("/create")
    public ResponseEntity<Void> createPost(@RequestBody PostRequestDto postRequestDto) throws SubredditNotFoundException, UserNameNotFoundException {
        postService.save(postRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping("/all")
    public ResponseEntity<List<PostResponseDto>> getAllPosts(){
        List<PostResponseDto> postResponseDtos = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) throws PostNotFoundException {
        PostResponseDto postResponseDto = postService.getPostById(id);
        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @GetMapping("/bySubreddit")
    public ResponseEntity<List<PostResponseDto>> getPostsBySubreddit(@RequestParam Long subredditId) throws SubredditNotFoundException {
        List<PostResponseDto> responseDto = postService.getPostsBySubredditId(subredditId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);

    }
    @GetMapping("/byUser")
    public ResponseEntity<List<PostResponseDto>> getPostsByUser(@RequestParam String userName) throws UserNameNotFoundException {
        List<PostResponseDto> responseDto = postService.getPostsByUser(userName);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
