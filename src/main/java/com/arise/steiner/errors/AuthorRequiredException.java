package com.arise.steiner.errors;

import com.arise.steiner.config.ApplicationProperties;
import org.springframework.http.HttpStatus;

import static com.arise.steiner.errors.ErrorKey.BUSINESS_LOGIC_INVALID;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class AuthorRequiredException extends SteinerRuntimeException {


    public AuthorRequiredException(ApplicationProperties app) {
        super(BUSINESS_LOGIC_INVALID,
                "Author required to perform operation, please provide using " + app.getSecurityMode(),
                FORBIDDEN);
    }

    public AuthorRequiredException(ApplicationProperties app, Throwable cause) {
        super(BUSINESS_LOGIC_INVALID,
                "Author required to perform operation, please provide using " + app.getSecurityMode(),
                cause,
                FORBIDDEN);
    }
}
