package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

/**
 * To be used when no key with value 'name' is found inside the request
 */
public class EmptyNameException extends SteinerRuntimeException {

    public EmptyNameException(ErrorKey errorKey, Class clazz) {
        super(errorKey, clazz.getName(), HttpStatus.EXPECTATION_FAILED);
    }
}
