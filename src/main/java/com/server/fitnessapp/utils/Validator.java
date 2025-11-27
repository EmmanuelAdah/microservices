package com.server.fitnessapp.utils;

import com.server.fitnessapp.dtos.requests.UserRequest;
import com.server.fitnessapp.exceptions.InvalidEmailFormatException;
import com.server.fitnessapp.exceptions.InvalidNameFormatException;
import java.util.regex.Pattern;

public class Validator {

    public static void validate(UserRequest userRequest) {
        validateName(userRequest.getFirstName(), "First name");
        validateName(userRequest.getLastName(), "Last name");

        if (!isValidEmail(userRequest.getEmail()))
            throw new InvalidEmailFormatException("Invalid email format");
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[A-Za-z0-9+._-]+@[A-Za-z]+\\.[A-Za-z]{2,3}$";
        return Pattern.matches(regex, email);
    }

    public static boolean isValidName(String name) {
        String regex = "^[A-Za-z]+.{2,20}]";
        return Pattern.matches(regex, name);
    }

    public static void validateName(String name, String field) {
        if (!isValidName(name))
            throw new InvalidNameFormatException(field + " must be between 3 and 20 characters long");
    }
}
