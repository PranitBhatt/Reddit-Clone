package com.example.RedditClone.contoller;


import com.example.RedditClone.dtos.AuthResponse;
import com.example.RedditClone.dtos.LoginRequest;
import com.example.RedditClone.exceptions.PasswordIsIncorrect;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.models.User;
import com.example.RedditClone.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/reddit/login")
@AllArgsConstructor
public class LoginController {

    private UserService userService;

    @PostMapping("/")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) throws UserNameNotFoundException, PasswordIsIncorrect {
        return userService.login(loginRequest);
    }

    @GetMapping("/currentUser")
    public ResponseEntity<User> getCurrentUser() throws UserNameNotFoundException {
        User ans = userService.getCurrentUser();
        return new ResponseEntity<>(ans,OK);
    }

}
