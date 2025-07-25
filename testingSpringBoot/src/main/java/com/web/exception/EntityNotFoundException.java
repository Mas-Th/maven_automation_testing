package com.web.exception;

public abstract class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String entityName, Object id) {
        super(entityName + " with ID " + id + " not found");
    }
}
