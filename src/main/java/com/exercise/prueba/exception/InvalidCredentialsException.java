package com.exercise.prueba.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
