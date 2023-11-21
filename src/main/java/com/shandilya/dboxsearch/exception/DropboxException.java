package com.shandilya.dboxsearch.exception;

public class DropboxException extends RuntimeException {
    public DropboxException(String message, Exception cause) {
        super(message, cause);
    }
}