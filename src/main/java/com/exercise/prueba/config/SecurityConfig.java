package com.exercise.prueba.config;

import com.exercise.prueba.security.AuthorizationFilter;
import com.exercise.prueba.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final AuthorizationFilter authorizationFilter;

    public SecurityConfig(UserService userService, AuthorizationFilter authorizationFilter) {
        this.userService = userService;
        this.authorizationFilter = authorizationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> userService.findByEmail(username));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/sign-up").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
