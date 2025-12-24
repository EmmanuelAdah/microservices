package com.server.aiservice.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Map;

@Data
@Builder
@Document(collection = "recommendations")
public class Recommendation {
    @Id
    private String id;

    private String activityId;
    private String userId;
    private Map<String, Object> analysis;
    private Map<String, Object> improvements;
    private Map<String, Object> suggestions;
    private List<String> safetyMeasures;
}
