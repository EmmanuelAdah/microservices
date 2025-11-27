package com.server.activity.exceptions;

public class NoActivityFoundException extends RuntimeException {
    public NoActivityFoundException(String message) {
        super(message);
    }
}

