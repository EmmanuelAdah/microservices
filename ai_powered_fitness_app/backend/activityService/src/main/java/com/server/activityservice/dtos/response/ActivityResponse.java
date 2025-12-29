package com.server.activityservice.dtos.response;


import com.server.activityservice.data.models.ActivityType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ActivityResponse {
    private String id;
    private String userId;
    private ActivityType activityType;
    private double duration;
    private double caloriesBurned;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMatrics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
