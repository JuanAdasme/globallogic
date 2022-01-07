package com.exercise.prueba.util;

import com.exercise.prueba.dto.UserDTO;
import com.exercise.prueba.model.User;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = Utils.class)
class UtilsTest {

    @Autowired
    private Utils utils;

    @Test
    void mapUserToUserDTO() {
        User user = new User();
        user.setName("Django Freeman");
        user.setEmail("django@test.cl");

        UserDTO userDTO = utils.mapUserToUserDTO(user);

        Assertions.assertEquals(userDTO.getName(), user.getName());
        Assertions.assertEquals(userDTO.getEmail(), user.getEmail());
    }

    @Test
    void whenValidPassword_thenReturnTrue() {
        String validPassword = "a2asfGfdfdf4";

        boolean isValid = utils.isValidPassword(validPassword);

        Assert.isTrue(isValid);
    }
}