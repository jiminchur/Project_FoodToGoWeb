package com.foodtogo.mono.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiException> handleIllegalArgumentException(IllegalArgumentException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<RestApiException> handleIllegalStateException(IllegalStateException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<RestApiException> handleNullPointerException(NullPointerException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(CustomAccessDeniedException.class)
    public ResponseEntity<RestApiException> handleCustomAccessDeniedException(CustomAccessDeniedException ex) {
        RestApiException restApiException = new RestApiException(ex.getMessage(), HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(
                restApiException,
                HttpStatus.FORBIDDEN
        );
    }
}