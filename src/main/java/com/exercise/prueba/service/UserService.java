package com.exercise.prueba.service;

import java.util.List;

import com.exercise.prueba.model.User;

public interface UserService {

	List<User> findAll();

	User save(User user);

	User findByEmail(String email);
}
