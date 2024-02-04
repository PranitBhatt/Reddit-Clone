package com.example.RedditClone.service;

import com.example.RedditClone.dtos.CustomUserDetail;
//import com.example.RedditClone.dtos.UserDetail;
import com.example.RedditClone.models.User;
import com.example.RedditClone.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.util.Collections.singletonList;

@Component
@Primary
//@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
//    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDetails  loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        return user.map(CustomUserDetail::new)
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));
//        User user = userOptional
//                .orElseThrow(() -> new UsernameNotFoundException("No user " +
//                        "Found with username : " + username));
        }
}
