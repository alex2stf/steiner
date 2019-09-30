package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

public class GraphQLDocException extends SteinerRuntimeException {

    public GraphQLDocException(ErrorKey errorKey, String message) {
        super(errorKey, message, HttpStatus.PRECONDITION_FAILED);
    }

    public GraphQLDocException(ErrorKey errorKey, String message, Throwable cause) {
        super(errorKey, message, cause, HttpStatus.PRECONDITION_FAILED);
    }
}
