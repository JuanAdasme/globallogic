package com.exercise.prueba.controller;

import com.exercise.prueba.dto.UserDTO;
import com.exercise.prueba.model.User;
import com.exercise.prueba.service.AuthService;
import com.exercise.prueba.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private AuthService authService;

    @PostMapping("/sign-up")
    public @ResponseBody
    ResponseEntity<User> save(@Valid @RequestBody User user) {
        return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid User user) {
        UserDTO authenticatedUser = authService.login(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authenticatedUser.getToken())
                .body(authenticatedUser);
    }

}
