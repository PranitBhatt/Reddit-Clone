package com.example.RedditClone.contoller;

import com.example.RedditClone.dtos.RegisterRequestDto;
import com.example.RedditClone.exceptions.UserAlreadyExists;
import com.example.RedditClone.models.User;
import com.example.RedditClone.repositories.UserRepository;
import com.example.RedditClone.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/reddit/register")
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto requestDto) throws UserAlreadyExists {
        User registerUser  = userService.registerUser(requestDto);
        return new ResponseEntity<>("User Registration Successful",OK);
    }

}
