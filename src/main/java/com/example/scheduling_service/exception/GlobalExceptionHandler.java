package com.example.scheduling_service.exception;

import com.example.scheduling_service.util.Response;
import com.example.scheduling_service.util.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String FAILED = "FAILED";

    @ExceptionHandler(value = {
            JobDoesNotExistException.class,
    })
    public ResponseEntity<Object> notAvailableHandler(Exception exception) {
        return ResponseHandler.generateResponse(new Response(
                exception.getMessage(), HttpStatus.NOT_FOUND, FAILED, null)
        );
    }
}
