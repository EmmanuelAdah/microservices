package com.server.aiservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.aiservice.models.Activity;
import com.server.aiservice.models.Recommendation;
import com.server.aiservice.repositories.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityAIService {
    private final AIService aiService;
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public Recommendation processRequest(Activity activity) throws JsonProcessingException {

        String prompt = generatePrompt(activity);
        String aiResponse = aiService.getAnswers(prompt);
        String response = processAIResponse(aiResponse);

        log.info("AI Response: {}", response);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseNode = mapper.readTree(response);

        StringBuilder analysis = processAnalysis(responseNode);
        Map<String, Object> improvemets = processImprovements(responseNode);
        Map<String, Object> suggestions = processSuggestions(responseNode);
        List<String> safetyMeasures = processSafety(responseNode);

        try {
            Recommendation recommentdation = Recommendation.builder()
                    .activityId(activity.getId())
                    .userId(activity.getUserId())
                    .improvements(improvemets)
                    .safetyMeasures(safetyMeasures)
                    .suggestions(suggestions)
                    .recommendation(analysis.toString().trim())
                    .build();

            return recommendationRepository.save(recommentdation);
        } catch(Exception ex) {
            return new Recommendation();
        }
    }

    private List<String> processSafety(JsonNode responseNode) {
        List<String> safetyMeasures = new ArrayList<>();
        JsonNode safetyMeasure = responseNode.get("safety");

        safetyMeasure.forEach(safety -> safetyMeasures.add(safety.asText()));
 
        return (safetyMeasures.isEmpty()) ? Collections.singletonList("No safety measures recommended at the moment")
                : safetyMeasures;
    }

    private Map<String, Object> processSuggestions(JsonNode responseNode) {
        Map<String, Object> suggestions = new HashMap<>();
        JsonNode suggestionsNode = responseNode.get("suggestions");

        suggestionsNode.forEach(suggestion -> {
            String workout = suggestion.get("workout").asText();
            String description = suggestion.get("description").asText();
            suggestions.put(workout, description);
        });

        return (suggestions.isEmpty()) ? Collections.singletonMap("Response", "No suggestions found")
                : suggestions;
    }

    private Map<String, Object> processImprovements(JsonNode responseNode) {
        Map<String, Object> improvements = new HashMap<>();

        JsonNode improvementsNode = responseNode.get("improvements");

        improvementsNode.forEach(improvemet -> {
            String area = improvemet.get("area").asText();
            String activity = improvemet.get("recommendation").asText();
            improvements.put(area + ": ", activity);
        });

        return (improvements.isEmpty()) ? Collections.singletonMap("", "No improvements needed for this activity at the moment")
                : improvements;
    }

    public StringBuilder processAnalysis(JsonNode responseNode) {
        StringBuilder fullAnalysis = new StringBuilder();

        try {
            JsonNode analysis = responseNode.path("Analysis");

            log.info("Analysis: {}", analysis);

            if (analysis.isObject()) {
                buildAnalysis(fullAnalysis, analysis, "Overall: ", "overall");
                buildAnalysis(fullAnalysis, analysis, "Pace: ", "pace");
                buildAnalysis(fullAnalysis, analysis, "Heart Rate: ", "heart rate");
                buildAnalysis(fullAnalysis, analysis, "Calories Burned: ", "calories");
            }
        }catch (Exception e){
            throw new RuntimeException("Error in processing analysis");
        }
        return fullAnalysis;
    }

    public void buildAnalysis(StringBuilder fullAnalysis, JsonNode analysis, String prefix, String key) {
        if (!analysis.path(key).isMissingNode()) {
            fullAnalysis.append(prefix)
                    .append(analysis.path(key).asText())
                    .append("\n");
        }
    }

    public String processAIResponse(String aiResponse) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(aiResponse);

        JsonNode textNode = rootNode.path("candidates")
                .get(0)
                .path("content")
                .path("parts")
                .get(0)
                .path("text");

        return textNode.asText()
                .replaceAll("(?s)```json\\s*", "")
                .replaceAll("```", "")
                .trim();
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
        } catch (Exception ex) {
            throw new RuntimeException("Failed to build AI prompt", ex);
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
