package com.brakkits.util;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/25/2020
 *
 * Exception for data access
 **/
public class InvalidDataException extends RuntimeException{
    public InvalidDataException() {
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
