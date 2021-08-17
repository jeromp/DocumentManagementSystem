package com.github.jeromp.DocumentManagementSystem.exception;

public class DocumentNotFoundException extends RuntimeException {
    public DocumentNotFoundException(String message){
        super(message);
    }
}
