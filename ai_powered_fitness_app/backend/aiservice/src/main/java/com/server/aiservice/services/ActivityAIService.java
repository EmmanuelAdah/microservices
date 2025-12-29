package com.server.aiservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.aiservice.models.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityAIService {
    private final AIService aiService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processRequest(Activity activity) {
        String prompt = generatePrompt(activity);
        String aiResponse = aiService.getAnswers(prompt);
        log.info("AI Response: {}", aiResponse);
    }

    public String generatePrompt(Activity activity) {
        var objectMapper = new ObjectMapper();
        try {
            Map<String, Object> payload = Map.of(
                "contents", new Object[]{
                        Map.of(
                            "parts", new Object[]{
                                    Map.of("text", promptRequest(activity))
                            }
                        )
                }
            );

            return objectMapper.writeValueAsString(payload);
        } catch (Exception e) {
            throw new RuntimeException("Failed to build AI prompt", e);
        }
    }

    private String promptRequest(Activity activity) {
        return """
                Could you provide a very precised answer for the following, based on the
                information provided from this activity:
                Activity Type: %s
                Duration: %s
                Calories Burned: %s
                Matrics: %s
               \s
                Provide the answers in the following json format:
               \s
                {  \s
                    Analysis: {
                        "overall": "overall analysis here",
                        "pace": "pace analysis here",
                        "heart rate": "heart rate analysis here",
                        "calories": "calories analysis here",
                    },
                    improvements: [
                        {
                            "area": "area of improvement here",
                            "recommendation": "recommendation for area of improvement here",
                        }
                    ],
                    suggestions: [
                        {
                            "workout": "workout suggestion here",
                            "description": "workout description here","\s
                        }
                    ],
                    safety: [
                        {
                            "list of safety measures"
                        }
                    ]
                }
               \s""".formatted(
                        activity.getActivityType(),
                        activity.getDuration(),
                        activity.getCaloriesBurned(),
                        activity.getAdditionalMatrics()
                );
    }
}
