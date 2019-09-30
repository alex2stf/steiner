package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

public class SteinerException extends Exception implements IException {

    private final ErrorKey errorKey;
    private final String message;
    private final HttpStatus status;

    public SteinerException(ErrorKey errorKey, String message, HttpStatus status, Throwable cause) {
        super(message);
        this.message = message;
        this.status = status;
        initCause(cause);
        this.errorKey = errorKey;
    }

    public SteinerException(ErrorKey errorKey, String message, HttpStatus status) {
        super(message);
        this.errorKey = errorKey;
        this.message = message;
        this.status = status;
    }

    public String getKey() {
        return errorKey.name();
    }

    @Override
    public int getCode() {
        return errorKey.ordinal();
    }


    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
