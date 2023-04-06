package com.example.API.exception;

public class NotEnoughVacationDaysException extends RuntimeException{
    public NotEnoughVacationDaysException(String message) {
        super(message);
    }
}
