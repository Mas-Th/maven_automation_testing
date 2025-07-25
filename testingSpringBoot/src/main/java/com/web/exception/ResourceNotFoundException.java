package com.web.exception;

// Không có annotation @ResponseStatus ở đây
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}