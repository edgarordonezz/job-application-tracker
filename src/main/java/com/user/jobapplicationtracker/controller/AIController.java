package com.user.jobapplicationtracker.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.jobapplicationtracker.service.AIService;

@RestController
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/score")
    public ResponseEntity<String> score(@RequestBody Map<String, String> body) {
        String resume = body.get("resume");
        String jobDescription = body.get("jobDescription");
        if (resume == null || jobDescription == null) {
            return ResponseEntity.badRequest().body("Missing resume or jobDescription");
        }
        String result = aiService.scoreJobMatch(resume, jobDescription);
        return ResponseEntity.ok(result);
    }

}
