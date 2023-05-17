package com.example.demo.exceptions;

public class UserBySourceValidationException extends RuntimeException {
    public UserBySourceValidationException(String message) {
        super(message);
    }
}
