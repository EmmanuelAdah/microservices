package com.server.activity.services;

import com.server.activity.data.models.Activity;
import com.server.activity.data.repositories.ActivityRepository;
import com.server.activity.dtos.requests.ActivityRequest;
import com.server.activity.dtos.response.ActivityResponse;
import com.server.activity.exceptions.NoActivityFoundException;
import com.server.activity.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.server.activity.utils.Mapper.map;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;

    @Override
    public ActivityResponse saveActivity(ActivityRequest request) {
        Activity activity = activityRepository.save(map(request));
        return map(activity);
    }

    @Override
    public List<ActivityResponse> findByUserId(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);

        if (activities.isEmpty())
            throw new NoActivityFoundException("No activities found");

        return activities.stream()
                .map(Mapper::map)
                .toList();
    }

    @Override
    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId)
                .map(Mapper::map)
                .orElseThrow(() -> new NoActivityFoundException("Activity not found"));
    }
}
