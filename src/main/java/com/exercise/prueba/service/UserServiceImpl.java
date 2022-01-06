package com.exercise.prueba.service;

import java.util.List;

import com.exercise.prueba.exception.InvalidPasswordFormatException;
import com.exercise.prueba.util.Utils;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.exercise.prueba.model.User;
import com.exercise.prueba.repository.UserRepository;

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
        return userRepository.findByEmail(email);
    }

}
