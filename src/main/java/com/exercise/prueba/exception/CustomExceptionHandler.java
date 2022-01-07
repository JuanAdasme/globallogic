package com.exercise.prueba.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleJdbcSQLIntegrityConstraintViolationException(DataIntegrityViolationException e) {
        return buildError(HttpStatus.CONFLICT, "The user already exists");
    }

    @ExceptionHandler(InvalidJWTException.class)
    protected ResponseEntity<Object> handleInvalidJWTException(InvalidJWTException e) {
        return buildError(HttpStatus.UNAUTHORIZED, "auth: invalid JWT");
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    protected ResponseEntity<Object> handleInvalidCredentialsExceptions(InvalidCredentialsException e) {
        return buildError(HttpStatus.UNAUTHORIZED, "auth: wrong username or password");
    }

    @ExceptionHandler(InvalidPasswordFormatException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(InvalidPasswordFormatException e) {
        List<String> passwordValidations = new ArrayList<>();
        passwordValidations.add("invalid password format");
        passwordValidations.add("size should be between 8 and 12");
        passwordValidations.add("should contain 2 numbers");
        passwordValidations.add("should contain 1 uppercase character");
        return buildError(HttpStatus.BAD_REQUEST, String.join("; ", passwordValidations));
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        return buildError(HttpStatus.UNAUTHORIZED, "auth: wrong username or password");
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        });

        String errorsMessage = String.join("; ", errors);

        return buildError(HttpStatus.BAD_REQUEST, errorsMessage);
    }

    private ResponseEntity<Object> buildError(HttpStatus httpStatus, String message) {
        Map<String, List> error = new HashMap<>();
        List<Map<String, Object>> errorList = new ArrayList<>();
        Map<String, Object> errorMap = new HashMap<>();
        errorMap.put("timestamp", LocalDateTime.now());
        errorMap.put("codigo", httpStatus.value());
        errorMap.put("detail", message);
        errorList.add(errorMap);
        error.put("error", errorList);
        return new ResponseEntity<>(error, httpStatus);
    }
}
