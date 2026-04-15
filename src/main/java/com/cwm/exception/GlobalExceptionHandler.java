package com.cwm.exception;

import com.cwm.dto.APIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult()
                .getFieldErrors()
                .forEach(error -> {
                    errors.put(error.getField(), error.getDefaultMessage());
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(EventNotFoundException.class)
    private ResponseEntity<APIResponse> handleEventNotFoundException(EventNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new APIResponse(e.getMessage()));
    }

    @ExceptionHandler(SubscriptionNotFoundException.class)
    private ResponseEntity<APIResponse> handleSubscriptionNotFoundException(SubscriptionNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new APIResponse(e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<APIResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        String message = Optional.ofNullable(e.getMessage())
                .filter(msg -> !msg.isBlank())
                .orElse("Invalid input, please try again");

        return ResponseEntity
                .badRequest()
                .body(new APIResponse(message));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<APIResponse> handleGenericExceptions(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponse("An unexpected error occurred. Please try again later."));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<APIResponse> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new APIResponse("Invalid username or password."));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<APIResponse> handleDisabled(DisabledException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new APIResponse("Account is deactivated. Please contact support."));
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    private ResponseEntity<APIResponse> handlePaymentNotFoundException(PaymentNotFoundException e) {
        return ResponseEntity.
                status(HttpStatus.NOT_FOUND)
                .body(new APIResponse(e.getMessage()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<APIResponse> handleMaxSizeException(
            MaxUploadSizeExceededException e) {

        return ResponseEntity
                .badRequest()
                .body(new APIResponse("File size must be less than 2MB."));
    }
}
