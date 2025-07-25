package com.web.exception;

// Không có annotation @ResponseStatus ở đây
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }
}