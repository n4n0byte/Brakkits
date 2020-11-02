package com.brakkits.util;

/**
 * Ali Cooper
 * brakkits
 * CST-452
 * 10/25/2020
 *
 * Exception for duplicate entries
 **/

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException() {
    }

    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEntryException(Throwable cause) {
        super(cause);
    }

    public DuplicateEntryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
