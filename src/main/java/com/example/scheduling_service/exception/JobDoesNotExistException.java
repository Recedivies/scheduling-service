package com.example.scheduling_service.exception;


public class JobDoesNotExistException extends RuntimeException {
    public JobDoesNotExistException(String id) {
        super("Job with id " + id + " does not exist");
    }
}
