package com.cwm.exception;

import com.cwm.dto.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<APIResponse> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new APIResponse(e.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    private ResponseEntity<APIResponse> handleInvalidTokenException(InvalidTokenException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new APIResponse(e.getMessage()));
    }
}
