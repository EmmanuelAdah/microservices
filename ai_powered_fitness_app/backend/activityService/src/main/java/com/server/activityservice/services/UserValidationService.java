package com.server.activityservice.services;

import com.server.activityservice.exceptions.InvalidUserRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserValidationService {
    private final WebClient userValidationWebClient;

    public void isValidUser(String userId) {
            Boolean isValidUser = userValidationWebClient
                    .get()
                    .uri("/api/user/validate/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (Boolean.FALSE.equals(isValidUser)) {
                throw new InvalidUserRequestException("Invalid request with id: " + userId);
            }
    }
}
