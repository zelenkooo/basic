package com.startup.server.exception;

public class GenericServiceException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3681636607322599994L;

    public GenericServiceException(String message) {
        super(message);
    }
}
