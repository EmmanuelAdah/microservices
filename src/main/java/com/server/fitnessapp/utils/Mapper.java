package com.server.fitnessapp.utils;


import com.server.fitnessapp.data.models.Role;
import com.server.fitnessapp.data.models.User;
import com.server.fitnessapp.dtos.reponse.UserResponse;
import com.server.fitnessapp.dtos.requests.UserRequest;
import static com.server.fitnessapp.utils.Validator.validate;

public class Mapper {

    public static User map(UserRequest request) {
        validate(request);

        return User.builder()
                .firstName(request.getFirstName().toUpperCase())
                .lastName(request.getLastName().toUpperCase())
                .userName(request.getUserName())
                .email(request.getEmail())
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .password(request.getPassword())
                .build();
    }

    public static UserResponse map(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setUsername(user.getUserName());
        userResponse.setEmail(user.getEmail());
        return userResponse;
    }
}
