package com.example.app.user.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(resourceName + " with ID [" + resourceId + "] was not found.");
    }
}
