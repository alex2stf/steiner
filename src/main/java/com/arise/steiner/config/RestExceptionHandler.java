package com.arise.steiner.config;

import com.arise.steiner.errors.ErrorKey;
import com.arise.steiner.errors.IException;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@SuppressWarnings("unused")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    public static final String FIELD_VALIDATION_EXCEPTION = "FIELD_VALIDATION_EXCEPTION";
    private final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);


    @Value("${application.debugEnabled:}")
    private String debugEnabled;

    public RestExceptionHandler() {
        super();
    }

    private boolean isDebugEnabled() {
        return "true".equals(debugEnabled);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        StringBuilder message = new StringBuilder();
        if (ex.getBindingResult() != null && ex.getBindingResult().getAllErrors() != null) {
            for (ObjectError error : ex.getBindingResult().getAllErrors()) {
                message.append(error.getDefaultMessage()).append(" ");
            }
        }

        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setKey(FIELD_VALIDATION_EXCEPTION);
        apiError.setErrorMessage(message.toString());

        if (isDebugEnabled()) {
            addStackTrace(ex, apiError);
        }

        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Throwable.class)
    protected ResponseEntity<Object> handleThrowableErrors(Throwable ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("Broken pipe")) {
            log.error("Broken pipe exception", ex);
            return null;
        }
        ApiError apiError = new ApiError();
        apiError.setErrorMessage(ex.getMessage());

        if (ex instanceof IException) {
            IException documentException = (IException) ex;
            apiError.validate(documentException);
        } else {
            apiError.setKey(ErrorKey.UNEXPECTED.name());
            apiError.setErrorCode(ErrorKey.UNEXPECTED.ordinal());
            apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (isDebugEnabled()) {
            addStackTrace(ex, apiError);
        }

        log.error("Controller exception", ex);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    /**
     * Add stacktrace to the response object
     */
    private void addStackTrace(Throwable ex, ApiError apiError) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        apiError.setStack(stringWriter.toString());
    }

    private class ApiError {

        private int errorCode;
        private String errorMessage;
        private String key;
        private HttpStatus status;
        private String stack;

        public void validate(IException e) {
            errorCode = e.getCode();
            errorMessage = e.getMessage();
            key = e.getKey();
            status = e.getStatus();
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getStack() {
            return stack;
        }

        public void setStack(String stack) {
            this.stack = stack;
        }
    }
}

