package com.server.fitnessapp.services;

import com.server.fitnessapp.dtos.reponse.UserResponse;
import com.server.fitnessapp.dtos.requests.UserRequest;

public interface UserService {

    UserResponse register(UserRequest request);
    UserResponse findById(String id);
}
