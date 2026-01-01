package com.server.aiservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private String id;
    private String userId;
    private String activityType;
    private double duration;
    private double caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMatrics;

}
