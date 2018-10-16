package com.hxs.service.exceptions;

/**
 * @author HSteidel
 */
public class UnauthorizedActionException extends RuntimeException {

    public UnauthorizedActionException(String message) {
        super(message);
    }

}
