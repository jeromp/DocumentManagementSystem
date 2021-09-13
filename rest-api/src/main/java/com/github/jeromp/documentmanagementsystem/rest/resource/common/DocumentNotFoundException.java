package com.github.jeromp.documentmanagementsystem.rest.resource.common;

import org.springframework.http.HttpStatus;

public class DocumentNotFoundException extends RuntimeException {
    private HttpStatus errorCode;

    public DocumentNotFoundException(HttpStatus errorCode, String message){
        this(errorCode, message, null);
    }

    public DocumentNotFoundException(HttpStatus errorCode, String message, Throwable cause){
        super(message, cause);
        this.errorCode = errorCode;
    }

    public HttpStatus getErrorCode(){
        return this.errorCode;
    }
}
