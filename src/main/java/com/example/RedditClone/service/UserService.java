package com.example.RedditClone.service;

import com.example.RedditClone.dtos.CustomUserDetail;
import com.example.RedditClone.dtos.LoginRequest;
import com.example.RedditClone.dtos.RegisterRequestDto;
import com.example.RedditClone.exceptions.PasswordIsIncorrect;
import com.example.RedditClone.exceptions.UserAlreadyExists;
import com.example.RedditClone.exceptions.UserNameNotFoundException;
import com.example.RedditClone.models.User;
import com.example.RedditClone.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private UserDetailServiceImpl userDetailService;
    private HttpSession session;

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

    //    @Transactional(readOnly = true)
    public User getCurrentUser() throws UserNameNotFoundException {
//        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null &&  authentication.isAuthenticated()) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
////            return "Auth";
//            return userRepository.findByUserName(userDetails.getUsername()).orElseThrow(()-> new UserNameNotFoundException("User name not found:"));
//        }
//        return "Not auth";
        return (User) session.getAttribute("User");
    }

    public User login(LoginRequest loginRequest) throws UserNameNotFoundException, PasswordIsIncorrect {
        User user = userRepository.findByUserName(loginRequest.getUsername())
                .orElseThrow(() -> new UserNameNotFoundException("UserName not found" + loginRequest.getUsername()));
//        if(!user.getPassword().equals(loginRequest.getPassword())){
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new PasswordIsIncorrect("Password is Incorrect");
        }

//         CustomUserDetail userDetails = (CustomUserDetail) userDetailService.loadUserByUsername(loginRequest.getUsername());
//
//        UsernamePasswordAuthenticationToken authReq =
//                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
//
//        Authentication auth = authenticationManager.authenticate(authReq);
//
//        SecurityContext sc = SecurityContextHolder.getContext();
//
//        sc.setAuthentication(auth);
//        HttpSession session = request.getSession(true);
//        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
//        if(){
        return user;
//        } else {
        // Authentication failed
//            return "Authentication failed";
//        }
//        Authentication authentication =  authenticationManager.authenticate(authenticationToken);
//
//        if(authentication.isAuthenticated()) {
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            return "User " + userDetails.getUsername()+ " logged in Successfully";
//        }
//        else {
//            return "Auth failed";
    }
}
