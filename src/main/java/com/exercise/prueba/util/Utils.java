package com.exercise.prueba.util;

import com.exercise.prueba.dto.UserDTO;
import com.exercise.prueba.exception.InvalidJWTException;
import com.exercise.prueba.model.User;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Utils {

    @Value("${security.jwt.secret}")
    private String jwtSecret;
    @Value("${security.jwt.issuer}")
    private String jwtIssuer;
    private static final String passwordRegExp = "^(?=[a-zA-Z\\d]{8,12}$)(?=[a-z]*)(?=(?:[a-zA-Z]*[\\d]))(?=(?:[a-z\\d]*[A-Z][a-z\\d]*))[a-zA-Z\\d]*$";

    public String getUsernameByToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[1];
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new InvalidJWTException("auth: invalid JWT", ex);
        }
    }

    public String generateAccessToken(User user) {
        return Jwts.builder()
                .setSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public UserDTO mapUserToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhones(user.getPhones());
        userDTO.setCreated(user.getCreated());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setToken(user.getToken());
        userDTO.setIsActive(user.getIsActive());
        return userDTO;
    }

    public boolean isValidPassword(String password) {
        return password.matches(passwordRegExp);
    }
}
