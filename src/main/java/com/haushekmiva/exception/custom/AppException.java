package com.haushekmiva.exception.custom;

public class AppException extends RuntimeException {
    public AppException(String message, Throwable e) {
        super(message, e);
    }
}
