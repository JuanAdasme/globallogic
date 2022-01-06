package com.exercise.prueba.controller;

import com.exercise.prueba.dto.UserDTO;
import com.exercise.prueba.model.User;
import com.exercise.prueba.service.UserService;
import com.exercise.prueba.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private Utils utils;

    @GetMapping("/hello")
    public @ResponseBody
    String helloWorld() {
        return "Hello, world!";
    }

    @GetMapping
    public @ResponseBody
    List<User> findAll() {
        return userService.findAll();
    }

    @PostMapping("/sign-up")
    public @ResponseBody
    ResponseEntity<User> save(@Valid @RequestBody User user) {
        return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid User user) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        UserDTO authenticatedUser = utils.mapUserToUserDTO((User) authenticate.getPrincipal());

        String newToken = utils.generateAccessToken(user);
        authenticatedUser.setToken(newToken);
        authenticatedUser.setLastLogin(LocalDateTime.now());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, newToken)
                .body(authenticatedUser);
    }

}
