package com.hxs.service.exceptions;

import java.util.UUID;

/**
 * @author HSteidel
 */
public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(String entityType, String entityId) {
        super(String.format("%s identified by '%s' does not exist", entityType, entityId));
    }

    public EntityNotFoundException(Class entityClass, String entityId) {
        super(String.format("%s identified by '%s' does not exist", entityClass.getName(), entityId));
    }

    public EntityNotFoundException(Class entityClass, Long id) {
        super(String.format("%s identified by '%s' does not exist", entityClass.getName(), String.valueOf(id)));
    }

    public EntityNotFoundException(Class entityClass, UUID uuid) {
        super(String.format("%s identified by '%s' does not exist", entityClass.getName(), uuid.toString()));
    }
}
