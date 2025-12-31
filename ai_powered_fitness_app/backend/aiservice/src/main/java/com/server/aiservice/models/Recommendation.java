package com.server.aiservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Document(collection = "recommendations")
@NoArgsConstructor
@AllArgsConstructor
public class Recommendation {
    @Id
    private String id;

    private String activityId;
    private String userId;
    private Map<String, Object> improvements;
    private Map<String, Object> suggestions;
    private List<String> safetyMeasures;
    private String recommendation;

    @CreatedDate
    private LocalDateTime createdAt;
}
