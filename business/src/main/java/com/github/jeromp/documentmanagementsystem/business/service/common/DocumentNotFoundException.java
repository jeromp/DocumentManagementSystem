package com.github.jeromp.documentmanagementsystem.business.service.common;

import org.springframework.http.HttpStatus;

public class DocumentNotFoundException extends RuntimeException {
    private HttpStatus errorCode = HttpStatus.NOT_FOUND;

    public DocumentNotFoundException(String message) {
        this(message, null);
    }

    public DocumentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatus getErrorCode() {
        return this.errorCode;
    }
}
