package com.brakkits.util;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/25/2020
 *
 * Exception for Security errors
 **/

public class SecurityException extends RuntimeException{
    public SecurityException() {
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
