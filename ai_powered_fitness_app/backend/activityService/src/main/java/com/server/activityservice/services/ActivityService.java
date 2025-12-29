package com.server.activityservice.services;

import com.server.activityservice.dtos.requests.ActivityRequest;
import com.server.activityservice.dtos.response.ActivityResponse;
import java.util.List;

public interface ActivityService {

    ActivityResponse saveActivity(ActivityRequest request);
    List<ActivityResponse> findByUserId(String userId);
    ActivityResponse getActivityById(String activityId);
}
