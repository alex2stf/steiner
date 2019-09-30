package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

public class SteinerRuntimeException extends RuntimeException implements IException {

    private final ErrorKey errorKey;
    private final HttpStatus status;

    public SteinerRuntimeException(ErrorKey errorKey, String message, HttpStatus status) {
        super(message);
        this.errorKey = errorKey;

        this.status = status;
    }

    public SteinerRuntimeException(ErrorKey errorKey, String message, Throwable cause, HttpStatus status) {
        super(message);
        this.status = status;
        initCause(cause);
        this.errorKey = errorKey;
    }

    @Override
    public int getCode() {
        return errorKey.ordinal();
    }

    @Override
    public String getKey() {
        return errorKey.name();
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
