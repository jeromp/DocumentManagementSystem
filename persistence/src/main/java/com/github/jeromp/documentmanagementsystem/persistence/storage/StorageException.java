package com.github.jeromp.documentmanagementsystem.persistence.storage;

import org.springframework.http.HttpStatus;

public class StorageException extends RuntimeException {
    private HttpStatus errorCode;

    public StorageException(HttpStatus errorCode, String message) {
        this(errorCode, message, null);
    }

    public StorageException(HttpStatus errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode() {
        return this.errorCode;
    }
}