package com.haushekmiva.exception.custom;

public class DuplicateEntryException extends InfrastructureException {
    public DuplicateEntryException(String message, Throwable e) {
        super(message, e);
    }
}
