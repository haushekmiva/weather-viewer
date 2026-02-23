package com.haushekmiva.exception.custom;

public class DataBaseException extends InfrastructureException {
    public DataBaseException(String message, Throwable e) {
        super(message, e);
    }
}
