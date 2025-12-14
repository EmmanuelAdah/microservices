package com.server.userservice.controllers;

import com.server.userservice.dtos.requests.UserRequest;
import com.server.userservice.dtos.response.UserResponse;
import com.server.userservice.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(UserRequest request){
        return ResponseEntity.ok(userService.saveUser(request));
    }

    @GetMapping("/find/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String userId){
        return ResponseEntity.ok(userService.findById(userId));
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> isExistingUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.existById(userId));
    }
}
