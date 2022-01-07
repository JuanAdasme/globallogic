package com.exercise.prueba.service;

import com.exercise.prueba.dto.UserDTO;
import com.exercise.prueba.model.User;
import com.exercise.prueba.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private Utils utils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDTO login(User user) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        UserDTO authenticatedUser = utils.mapUserToUserDTO((User) authenticate.getPrincipal());

        String newToken = utils.generateAccessToken(user);
        authenticatedUser.setToken(newToken);
        authenticatedUser.setLastLogin(LocalDateTime.now());

        return authenticatedUser;
    }
}
