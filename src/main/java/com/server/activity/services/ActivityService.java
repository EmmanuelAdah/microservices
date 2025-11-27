package com.server.activity.services;

import com.server.activity.dtos.requests.ActivityRequest;
import com.server.activity.dtos.response.ActivityResponse;
import java.util.List;

public interface ActivityService {

    ActivityResponse saveActivity(ActivityRequest request);
    List<ActivityResponse> findByUserId(String userId);
    ActivityResponse getActivityById(String activityId);
}
