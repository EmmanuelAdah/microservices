package com.server.activity.dtos.requests;

import lombok.Data;
import java.util.Map;

@Data
public class ActivityRequest {
    private String userId;
    private String activityType;
    private double duration;
    private double caloriesBurned;
    private String startTime;
    private Map<String, Object> additionalMatrics;

}
