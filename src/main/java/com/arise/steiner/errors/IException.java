package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

public interface IException {

    int getCode();

    String getMessage();

    String getKey();

    HttpStatus getStatus();
}
