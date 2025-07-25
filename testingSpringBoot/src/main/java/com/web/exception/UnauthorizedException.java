package com.web.exception;

// Không có annotation @ResponseStatus ở đây
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super(message);

    }
}