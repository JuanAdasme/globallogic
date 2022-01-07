package com.exercise.prueba.service;

import com.exercise.prueba.exception.InvalidCredentialsException;
import com.exercise.prueba.exception.InvalidPasswordFormatException;
import com.exercise.prueba.model.User;
import com.exercise.prueba.repository.UserRepository;
import com.exercise.prueba.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserServiceImpl.class)
class UserServiceTest {

    @Autowired
    private UserServiceImpl userService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private Utils utils;

    @Test
    void findAll() {
        List<User> mockedUsers = new ArrayList<>();
        User user1 = new User();
        User user2 = new User();
        mockedUsers.add(user1);
        mockedUsers.add(user2);

        Mockito.when(userRepository.findAll()).thenReturn(mockedUsers);

        List<User> mockedResponse = userService.findAll();

        Assertions.assertEquals(mockedUsers.size(), mockedResponse.size());
    }

    @Test
    void whenValidUser_thenReturnUser() {
        String initialPassword = "a2asfGfdfdf4";
        String hashedPassword = "hashedPassword";
        User mockedUser = new User();
        mockedUser.setName("King Schultz");
        mockedUser.setEmail("drschultz@test.com");
        mockedUser.setPassword(initialPassword);

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(mockedUser);
        Mockito.when(utils.isValidPassword(Mockito.any())).thenReturn(true);
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn(hashedPassword);

        User userResponse = userService.save(mockedUser);

        Assertions.assertNotEquals(initialPassword, userResponse.getPassword());
    }

    @Test
    void whenEmptyPassword_thenThrowException() {
        String emptyPassword = "";
        User mockedUser = new User();
        mockedUser.setName("King Schultz");
        mockedUser.setEmail("drschultz@test.com");
        mockedUser.setPassword(emptyPassword);

        Mockito.when(utils.isValidPassword(Mockito.any())).thenReturn(false);

        InvalidPasswordFormatException thrown = Assertions.assertThrows(InvalidPasswordFormatException.class, () -> {
            userService.save(mockedUser);
        });

        Assertions.assertNotEquals("invalid password format", thrown.getMessage());
    }

    @Test
    void whenUserExists_thenReturnUser() {
        String email = "drschultz@test.com";
        User mockedUser = new User();
        mockedUser.setName("King Schultz");
        mockedUser.setEmail(email);
        mockedUser.setPassword("a2asfGfdfdf4");

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(mockedUser);

        User userResponse = userService.findByEmail(email);

        Assertions.assertEquals(mockedUser.getName(), userResponse.getName());
    }

    @Test
    void whenUserDoesNotExist_thenReturnUser() {
        String email = "drschultz@test.com";

        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(null);

        InvalidCredentialsException thrown = Assertions.assertThrows(InvalidCredentialsException.class, () -> {
            userService.findByEmail(email);
        });

        Assertions.assertEquals("wrong username or password", thrown.getMessage());
    }
}
