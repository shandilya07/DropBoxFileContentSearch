package com.shandilya.hevo.exception;

public class DropboxException extends RuntimeException {
    public DropboxException(String message, Exception cause) {
        super(message, cause);
    }
}