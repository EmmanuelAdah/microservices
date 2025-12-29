package com.server.activityservice.exceptions;

public class NoActivityFoundException extends RuntimeException {
    public NoActivityFoundException(String message) {
        super(message);
    }
}

