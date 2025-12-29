package com.server.activityservice.services;


import com.server.activityservice.data.models.Activity;
import com.server.activityservice.data.repositories.ActivityRepository;
import com.server.activityservice.dtos.requests.ActivityRequest;
import com.server.activityservice.dtos.response.ActivityResponse;
import com.server.activityservice.exceptions.NoActivityFoundException;
import com.server.activityservice.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.server.activityservice.utils.Mapper.map;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Override
    public ActivityResponse saveActivity(ActivityRequest request) {
        userValidationService.isValidUser(request.getUserId());

        Activity activity = activityRepository.save(map(request));
        rabbitTemplate.convertAndSend(activity);
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
