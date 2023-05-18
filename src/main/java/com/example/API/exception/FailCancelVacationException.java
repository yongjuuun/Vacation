package com.example.API.exception;

public class FailCancelVacationException extends RuntimeException {
    public FailCancelVacationException(String msg, Throwable t) {
        super(msg, t);
    }

    public FailCancelVacationException(String msg) {
        super(msg);
    }

    public FailCancelVacationException() {
        super();
    }
}
