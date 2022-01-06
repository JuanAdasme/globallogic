package com.exercise.prueba.service;

import com.exercise.prueba.exception.InvalidCredentialsException;
import com.exercise.prueba.exception.InvalidPasswordFormatException;
import com.exercise.prueba.model.User;
import com.exercise.prueba.repository.UserRepository;
import com.exercise.prueba.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Utils utils;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        if (!utils.isValidPassword(user.getPassword())) {
            throw new InvalidPasswordFormatException("Invalid password format", new Exception());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new InvalidCredentialsException("wrong username or password", new Exception());
        }
        return user;
    }

}
