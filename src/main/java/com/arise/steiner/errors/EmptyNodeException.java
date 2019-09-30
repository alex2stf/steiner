package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

public class EmptyNodeException extends SteinerRuntimeException {


    public EmptyNodeException(ErrorKey errorKey) {
        super(errorKey, "Empty document", HttpStatus.PRECONDITION_FAILED);
    }
}
