package com.server.fitnessapp.exceptions;

public class InvalidNameFormatException extends RuntimeException {
    public InvalidNameFormatException(String message) {
        super(message);
    }
}
