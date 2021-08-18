package com.github.jeromp.DocumentManagementSystem.storage;

public class DuplicateFileException extends StorageException {

    public DuplicateFileException(String message, int errorCode){
        super(message, errorCode);
    }
}
