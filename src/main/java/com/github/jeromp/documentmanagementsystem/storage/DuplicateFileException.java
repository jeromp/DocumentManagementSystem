package com.github.jeromp.documentmanagementsystem.storage;

import org.springframework.http.HttpStatus;

public class DuplicateFileException extends StorageException {

    public DuplicateFileException(HttpStatus errorCode, String message){
        super(errorCode, message, null);
    }
}
