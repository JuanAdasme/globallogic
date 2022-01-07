package com.exercise.prueba.service;

import com.exercise.prueba.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User save(User user);

    User findByEmail(String email);
}
