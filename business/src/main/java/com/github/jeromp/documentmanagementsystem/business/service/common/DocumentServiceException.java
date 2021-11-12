package com.github.jeromp.documentmanagementsystem.business.service.common;

import org.springframework.http.HttpStatus;

public class DocumentServiceException extends RuntimeException {
    private HttpStatus errorCode;

    public DocumentServiceException(HttpStatus errorCode, String message) {
        this(errorCode, message, null);
    }

    public DocumentServiceException(HttpStatus errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode() {
        return this.errorCode;
    }
}