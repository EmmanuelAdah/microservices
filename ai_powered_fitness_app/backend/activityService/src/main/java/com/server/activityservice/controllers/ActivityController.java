package com.server.activityservice.controllers;


import com.server.activityservice.dtos.requests.ActivityRequest;
import com.server.activityservice.dtos.response.ActivityResponse;
import com.server.activityservice.services.ActivityServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    private final ActivityServiceImpl activityService;

    public ActivityController(ActivityServiceImpl activityService) {
        this.activityService = activityService;
    }

    @PostMapping("/save")
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request) {
        return ResponseEntity.ok(activityService.saveActivity(request));
    }

    @GetMapping("/get/activities")
    public ResponseEntity<List<ActivityResponse>> getUserActivity(@RequestHeader("X-User-ID") String userId) {
        return ResponseEntity.ok(activityService.findByUserId(userId));
    }

    @GetMapping("/api/getById/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable String activityId) {
        return ResponseEntity.ok(activityService.getActivityById(activityId));
    }
}
