package com.arise.steiner.errors;

import org.springframework.http.HttpStatus;

public class InvalidCredentials extends SteinerException {

  public InvalidCredentials(Class clazz) {
    super(ErrorKey.INVALID_CREDENTIALS, "Invalid credentials for entity " + clazz.getSimpleName(), HttpStatus.FORBIDDEN);
  }
}
