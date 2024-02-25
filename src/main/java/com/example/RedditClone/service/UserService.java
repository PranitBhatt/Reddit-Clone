package com.example.RedditClone.service;

import com.example.RedditClone.dtos.AuthResponse;
import com.example.RedditClone.dtos.LoginRequest;
import com.example.RedditClone.dtos.RegisterRequestDto;
import com.example.RedditClone.exceptions.PasswordIsIncorrect;
import com.example.RedditClone.exceptions.UserAlreadyExists;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.models.User;
import com.example.RedditClone.repositories.UserRepository;
import com.example.RedditClone.security.generateJwtToken;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private generateJwtToken jwtProvider;

    public User registerUser(RegisterRequestDto requestDto) throws UserAlreadyExists {
        Optional<User> existingUser = userRepository.findByEmail(requestDto.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExists();
        }
        User user = new User();
        user.setUserName(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setCreated(Instant.now());

        userRepository.save(user);
        return user;
    }

        @Transactional(readOnly = true)
    public User getCurrentUser() throws UserNameNotFoundException {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUserName(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));

//        return (User) session.getAttribute("User");
    }

    public AuthResponse login(LoginRequest loginRequest) throws UserNameNotFoundException, PasswordIsIncorrect {
        User user = userRepository.findByUserName(loginRequest.getUsername())
                .orElseThrow(() -> new UserNameNotFoundException("UserName not found" + loginRequest.getUsername()));
//        if(!user.getPassword().equals(loginRequest.getPassword())){
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new PasswordIsIncorrect("Password is Incorrect");
        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return AuthResponse.builder()
                .authenticationToken(token)
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

}
