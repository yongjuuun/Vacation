package com.example.API.exception;

public class VacationNotFoundException extends RuntimeException{
    public VacationNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }

    public VacationNotFoundException(String msg) {
        super(msg);
    }

    public VacationNotFoundException() {
        super();
    }
}
