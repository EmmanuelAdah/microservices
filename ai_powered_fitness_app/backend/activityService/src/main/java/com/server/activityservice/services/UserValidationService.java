package com.server.activityservice.services;

import com.server.activityservice.exceptions.InvalidUserRequestException;
import com.server.activityservice.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationService {
    private final WebClient userValidationWebClient;

    public void isValidUser(String userId) {
        try {
            Boolean isValidUser = userValidationWebClient
                    .get()
                    .uri("/api/user/validate/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();

            if (Boolean.FALSE.equals(isValidUser)) {
                throw new InvalidUserRequestException("Invalid request with id: " + userId);
            }

        } catch (WebClientResponseException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new UserNotFoundException("User not found with id: " + userId);
            }else if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new InvalidUserRequestException("Invalid request with id: " + userId);
            }
        }
    }
}
