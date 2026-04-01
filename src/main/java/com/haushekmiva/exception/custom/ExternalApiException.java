package com.haushekmiva.exception.custom;

import lombok.Getter;

@Getter
public class ExternalApiException extends InfrastructureException {
    private final String serviceName;
    private final int httpStatus;

    public ExternalApiException(String message, Throwable e, String serviceName, int errorCode) {
        super(message, e);
        this.serviceName = serviceName;
        this.httpStatus = errorCode;
    }
}
