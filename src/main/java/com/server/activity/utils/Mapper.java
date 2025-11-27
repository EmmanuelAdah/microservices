package com.server.activity.utils;

import com.server.activity.data.models.Activity;
import com.server.activity.data.models.ActivityType;
import com.server.activity.dtos.requests.ActivityRequest;
import com.server.activity.dtos.response.ActivityResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mapper {

    public static Activity map(ActivityRequest request) {
        return Activity.builder()
        .userId(request.getUserId())
        .activityType(ActivityType.valueOf(request.getActivityType().toUpperCase( )))
        .duration(request.getDuration())
        .startTime(datetimeFormatter(request.getStartTime()))
        .additionalMatrics(request.getAdditionalMatrics())
        .caloriesBurned(request.getCaloriesBurned())
        .build();
    }

    public static ActivityResponse map(Activity activity) {
        return ActivityResponse.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getActivityType())
                .duration(activity.getDuration())
                .startTime(activity.getStartTime())
                .additionalMatrics(activity.getAdditionalMatrics())
                .caloriesBurned(activity.getCaloriesBurned())
                .build();
    }

    public static LocalDateTime datetimeFormatter(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTime, formatter);
    }
}
