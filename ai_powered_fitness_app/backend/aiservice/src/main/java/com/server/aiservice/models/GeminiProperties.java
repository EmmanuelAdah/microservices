package com.server.aiservice.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "gemini.api")
public class GeminiProperties {
    private String url;
    private String key;
}
