package com.server.userservice.utils;

import com.server.userservice.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, Object> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> response = new  HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", 404);
        response.put("httpStatus", HttpStatus.NOT_FOUND);
        return response;
    }
}
