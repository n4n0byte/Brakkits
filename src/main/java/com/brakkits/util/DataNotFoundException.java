package com.brakkits.util;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/18/2020
 *
 * Exception for data access
 **/
public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
