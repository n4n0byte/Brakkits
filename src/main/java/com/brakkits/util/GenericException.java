package com.brakkits.util;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/25/2020
 *
 * Exception for data access
 **/

public class GenericException extends RuntimeException {

    public GenericException() {
    }

    public GenericException(Throwable cause) {
        super(cause);
    }

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }
}
