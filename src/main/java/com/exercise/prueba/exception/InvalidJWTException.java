package com.exercise.prueba.exception;

public class InvalidJWTException extends RuntimeException {

    public InvalidJWTException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
