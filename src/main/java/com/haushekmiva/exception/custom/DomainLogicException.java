package com.haushekmiva.exception.custom;

public class DomainLogicException extends AppException {
    public DomainLogicException(String message, Throwable e) {
        super(message, e);
    }
}
