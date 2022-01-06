package com.exercise.prueba.controller;

import com.exercise.prueba.model.User;
import com.exercise.prueba.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/hello")
	public @ResponseBody String helloWorld() {
		return "Hello, world!";
	}

	@GetMapping
	public @ResponseBody List<User> findAll() {
		return userService.findAll();
	}

	@PostMapping
	public @ResponseBody ResponseEntity<User> save(@Valid @RequestBody User user) {
		return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public @ResponseBody User login(@RequestBody User user) {
		String token = getJWTToken(user.getEmail());
		user.setEmail(user.getEmail());
		user.setToken(token);
		return user;
	}

	private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}
