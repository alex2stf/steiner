package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends SteinerException {

    public EntityNotFoundException(Class clazz) {
        super(ErrorKey.ENTITY_NOT_FOUND, "Count not find entity " + clazz.getSimpleName(), HttpStatus.NOT_FOUND);
    }


}
