package com.server.aiservice.services;

import com.server.aiservice.models.GeminiProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AIService {
    private final WebClient webClient;
    private final GeminiProperties geminiProperties;

    public AIService(WebClient.Builder webClientConfig,
                     GeminiProperties geminiProperties) {
        this.webClient = webClientConfig.build();
        this.geminiProperties = geminiProperties;
    }

    public String getAnswers(String prompt) {
        return webClient.post()
                .uri(geminiProperties.getUrl())
                .header("x-goog-api-key", geminiProperties.getKey())
                .header("content-type", "application/json")
                .bodyValue(prompt)
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
}
