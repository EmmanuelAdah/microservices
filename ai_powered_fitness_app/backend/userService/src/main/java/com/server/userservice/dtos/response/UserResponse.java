package com.server.userservice.dtos.response;

import lombok.Data;

@Data
public class UserResponse {
    private String userId;
    private String email;
}
