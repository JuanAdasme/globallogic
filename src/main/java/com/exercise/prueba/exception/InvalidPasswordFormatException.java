package com.exercise.prueba.exception;

public class InvalidPasswordFormatException extends RuntimeException {

    public InvalidPasswordFormatException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
