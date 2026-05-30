package com.user.jobapplicationtracker.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.user.jobapplicationtracker.models.JobApplication;

@Service
public class AIService {

    @Value("${gemini.api-key}")
    private String apiKey;

    private static final String API_URL = 
        "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=";

    public String scoreJobMatch (String resume, String jobDescription) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String prompt = String.format("""
                    Given this resume and job description, score how well the candidate
                    matches the role from 0-100. Explain the score briefly and list
                    any missing keywords or skills.
                    Resume: %s
                    Job Description: %s
                    """, resume, jobDescription);

            // Build request body
            Map<String, Object> textPart = Map.of("text", prompt);
            Map<String, Object> parts = Map.of("parts", List.of(textPart));
            Map<String, Object> body = Map.of("contents", List.of(parts));

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            // Call gemini
            ResponseEntity<Map> response = restTemplate.postForEntity(
                API_URL + apiKey, request, Map.class);

            // Parse response
            List<Map<String, Object>> candidates =
                (List<Map<String, Object>>) response.getBody().get("candidates");

            Map<String, Object> content =
                (Map<String, Object>) candidates.get(0).get("content");

            List<Map<String, Object>> responseParts =
                (List<Map<String, Object>>) content.get("parts");

            return (String) responseParts.get(0).get("text");

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Gemini API error: " + e.getMessage());
        }
    }
}
