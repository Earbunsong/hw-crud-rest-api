package com.example.mbanking.security;

import com.example.mbanking.api.auth.AuthMapper;
import com.example.mbanking.api.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {

        log.info("loadUserByUsername: {}", username);
        log.info("loadUserByUsername: {}", authMapper.loadUserByUsername(username));

        User user = authMapper.loadUserByUsername(username).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User is not valid"));

        log.info("User: {}", user);

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        log.info("Custom User Details: {}", customUserDetails);

        return customUserDetails;
    }
}