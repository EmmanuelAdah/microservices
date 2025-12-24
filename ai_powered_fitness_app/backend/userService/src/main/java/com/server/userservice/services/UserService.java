package com.server.userservice.services;

import com.server.userservice.dtos.requests.UserRequest;
import com.server.userservice.dtos.response.UserResponse;

public interface UserService {

    UserResponse saveUser(UserRequest request);
    UserResponse findById(String userId);
    Boolean existById(String userId);
}
