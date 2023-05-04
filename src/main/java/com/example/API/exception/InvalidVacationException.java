package com.example.API.exception;

public class InvalidVacationException extends RuntimeException{
    public InvalidVacationException(String message) {
        super(message);
    }
}
