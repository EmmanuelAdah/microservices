package com.server.fitnessapp.controllers;

import com.server.fitnessapp.dtos.reponse.UserResponse;
import com.server.fitnessapp.dtos.requests.UserRequest;
import com.server.fitnessapp.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/fitness")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(UserRequest request) {
        return ResponseEntity.ok(userServiceImpl.register(request));
    }

    @GetMapping(value = "/getUserById/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userServiceImpl.findById(id));
    }
}


