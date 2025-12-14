package com.server.userservice.utils;

import com.server.userservice.dtos.requests.UserRequest;
import com.server.userservice.exceptions.InvalidEmailFormatException;
import com.server.userservice.exceptions.InvalidNameFormatException;
import java.util.regex.Pattern;

public class Validator {

    public static void isValidRequest(UserRequest request) {
        isValidName(request.getFirstName(), "Firstname");
        isValidName(request.getLastName(), "Lastname");
        isValidEmail(request.getEmail());
    }

    public static void isValidName(String name, String field) {
        String regex = "^[A-Za-z]{2,20}$";
        boolean pattern = Pattern.matches(regex, name);
        if(!pattern)
            throw new InvalidNameFormatException("Invalid name format for " + field);
    }

    public static void isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]{4,10})\\.[a-zA-Z]{2,3}$";
        if(!Pattern.matches(regex, email))
            throw new InvalidEmailFormatException("Invalid email format for " + email);
    }
}
