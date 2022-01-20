package com.github.jeromp.documentmanagementsystem.persistence.model;

import org.springframework.http.HttpStatus;

public class IllegalRelationshipException extends RuntimeException {
    private HttpStatus errorCode;

    public IllegalRelationshipException(HttpStatus errorCode, String message) {
        this(errorCode, message, null);
    }

    public IllegalRelationshipException(HttpStatus errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}
