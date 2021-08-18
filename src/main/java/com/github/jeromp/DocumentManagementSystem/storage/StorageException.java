package com.github.jeromp.DocumentManagementSystem.storage;

public class StorageException extends RuntimeException {
    private int errorCode = 400;

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public StorageException(String message, int errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}