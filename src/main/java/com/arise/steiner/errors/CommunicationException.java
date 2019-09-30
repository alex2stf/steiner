package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

public class CommunicationException extends SteinerException {

    public CommunicationException(ErrorKey errorKey, String message, Throwable cause) {
        super(errorKey, message, HttpStatus.BAD_REQUEST, cause);
    }

    public CommunicationException(ErrorKey errorKey, String message) {
        super(errorKey, message, HttpStatus.BAD_REQUEST);
    }
}
