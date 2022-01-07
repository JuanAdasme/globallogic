package com.exercise.prueba.service;

import com.exercise.prueba.dto.UserDTO;
import com.exercise.prueba.model.User;

public interface AuthService {

    UserDTO login(User user);
}
