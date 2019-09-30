package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

public class LogicalError extends SteinerException {

    public LogicalError(ErrorKey errorKey, String message) {
        super(ErrorKey.BUSINESS_LOGIC_INVALID, message, HttpStatus.FORBIDDEN);
    }
}
