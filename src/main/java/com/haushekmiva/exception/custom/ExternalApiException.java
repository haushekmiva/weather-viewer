package com.haushekmiva.exception.custom;

import lombok.Getter;

@Getter
public class ExternalApiException extends InfrastructureException {
    private final int httpStatus;

    public ExternalApiException(String message, int errorCode) {
        super(message);
        this.httpStatus = errorCode;
    }
}
